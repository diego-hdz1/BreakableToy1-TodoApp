import React, { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import axios from "axios";
import { useLocation, useNavigate } from 'react-router-dom';

function TableData({data, setData, nameFilter, filterPriority, filterDone,pagination, ordenation, handleOrdenation, dateSort ,handleDateSort, fetchStats}){

  const [shouldFetch, setShouldFetch] = useState(false);
  const location = useLocation();

  function setToDoStatus(record){
    
    if(!record.status){ //Esta todavia activo
      axios.put(`http://localhost:9090/todos/${record.id}/undone`).then((response) => { 
        console.log(response.data);
        fetchStats();
        navigator('/');
    });
    }else{ //Se acaba de poner como terminada 
      axios.put(`http://localhost:9090/todos/${record.id}/done`).then((response) => { 
        console.log(response.data);
        fetchStats();
        navigator('/');
    });
    }
    console.log(record)
    

  }

    //CHECR CODIGO REPETIDO, PORQUE LA FUNCION ESTA Y DE ABAJO SON CASI IGUALES, TAL VEZ SE MANDA COMO PARAMETRO CUAL MODIFICAR

  function handlePriority(){
    console.log(ordenation);
    if(ordenation === 1){
      handleOrdenation(ordenation + 1);
    }else if(ordenation === 2){
      handleOrdenation(ordenation + 1);
    }else if(ordenation === 3){
      handleOrdenation(1);
    }
    //PENSAR BIEN ESTO DE LOS QUE SE ORDENAN, PORQUE PUEDEN SER DIFERENTES  
    let url = `http://localhost:9090/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
      axios.get(url).then((response)=>{
          setData(response.data);
      }).catch(error =>{console.log(error);})
      console.log(nameFilter, filterPriority, filterDone);
      navigator('/');
  }

  //CHECR CODIGO REPETIDO, PORQUE LA FUNCION DE ARRIBA Y ESTA SON CASI IGUALES, TAL VEZ SE MANDA COMO PARAMETRO CUAL MODIFICAR

  function handleDate(){
    if(dateSort === 1){
      handleDateSort(dateSort + 1);
    }else if(dateSort === 2){
      handleDateSort(dateSort + 1);
    }else if(dateSort === 3){
      handleDateSort(1);
    }
    let url = `http://localhost:9090/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
      axios.get(url).then((response)=>{
          setData(response.data);
      }).catch(error =>{console.log(error);})
      console.log(nameFilter, filterPriority, filterDone);
      navigator('/');
  }

  const columns = [
    {
      title: 'Done',
      dataIndex: 'check',
      align: 'center',
      
      //Ver ids de cada input
      render: (text, record) => (
        <div>
          <input type="checkbox" id="cbox1" value="first_checkbox" defaultChecked={!record.status} onClick={()=>setToDoStatus(record)}/> 
        </div>
      )
    },
    {
      title: 'Name',
      dataIndex: 'text',
      align: 'center',
      render: (text, record) => (
        <span 
        style={{textDecoration : !record.status ? 'line-through' : 'none', textDecorationThickness : '2px'}}>
        {text}
        </span>
      )
    },
    {
      title: 'Priority',
      dataIndex: 'priority',
      align: 'center',

      sorter: {
        //Solo para que muestre el mensaje y cambie el cursor en el header
      },
      onHeaderCell: () => ({
        onClick: () => handlePriority()
      }),
      render: (text, record) => (
        <span>
        {text == 1 ? "Low" : text == 2 ? "Medium" : "High"}
        </span>
      )
      
      
    },
    {
      title: 'Due date',
      dataIndex: 'dueDate',
      align: 'center',

      sorter: {
        //Solo para que se muestre el mensaje y cambie el cursor en el header
      },

      //VALIDAR ESTO PORQUE PUEDE QUE NO HAYA DUE DATE Y SE SALE 
      render: (text, record) => (
        <span >
        {text !== null ? text.substring(0,10) : null}
        </span>
      ),
      onHeaderCell: () => ({
        onClick: () => handleDate()
      }),
    },
    {
      title: 'Done date',
      dataIndex: 'doneDate',
      align: 'center',

    },
    ,
    {
      title: 'Creation date',
      dataIndex: 'creationDate',
      align: 'center',

    },
    {
      title: 'Actions',
      dataIndex: 'actions',
      align: 'center',
      
      render: (text, record) => (
        <div>
          <Button type='primary' onClick={()=> updateToDo(record.id)} style={{marginRight : 8}}>Editar</Button>
          <Button type='danger' onClick={()=> deleteToDo(record.id)} style={{marginRight : 8, backgroundColor: 'red', color : 'white'}}>Eliminar</Button>
        </div>
      )
  
    },
  ];
  const navigator = useNavigate();

  function updateToDo(toDoId){
    navigator(`/edit-todo/${toDoId}`);
  }

  function deleteToDo(toDoId){
    console.log(toDoId);
    axios.delete(`http://localhost:9090/todos/${toDoId}`).then((response) => { 
      console.log(response.data);
      fetchStats();
      navigator('/');
  });
  }

  const onChange = (pagination, filters, sorter, extra) => {
    console.log('params', pagination, filters, sorter, extra);
  };
  
  

    useEffect(()=>{
      console.log("Entro");
      axios.get(`http://localhost:9090/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`).then((response)=>{
      setData(response.data);
        }).catch(error =>{console.log(error);})
    }, [location]);
    
    return(
        <Table columns={columns} dataSource={data}  onChange={onChange} pagination={false} rowKey={(record)=> record.id}/>
    );
}


export default TableData;