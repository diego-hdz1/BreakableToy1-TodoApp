import React, { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import axios from "axios";
import { useLocation, useNavigate } from 'react-router-dom';

function TableData({data, setData, nameFilter, filterPriority, filterDone,pagination}){

  const [shouldFetch, setShouldFetch] = useState(false);
  const location = useLocation();

  /*
  useEffect(()=>{
    if(!shouldFetch) return;
    if(id){
      axios.put(`http://localhost:8080/todos/${record.id}/undone`).then((response) => { 
        console.log(response.data);
        navigator('/');
    });
    }else{
      axios.put(`http://localhost:8080/todos/${record.id}/done`).then((response) => { 
        console.log(response.data);
        navigator('/');
    });
    }
})*/

  //Cuando le doy click esta mal, hay un error raro del use effect hook. Maximum depth exceeded
  //Your Effect Depends On a Function That’s Declared Inside the Component
  function setToDoStatus(record){
    
    // if(!record.status){ //Esta todavia activo
    //   axios.put(`http://localhost:8080/todos/${record.id}/done`).then((response) => { 
    //     console.log(response.data);
    //     navigator('/');
    // });
    // }else{ //Se acaba de poner como terminada 
    //   axios.put(`http://localhost:8080/todos/${record.id}/undone`).then((response) => { 
    //     console.log(response.data);
    //     navigator('/');
    // });
    // }
    // console.log(record)
    

  }

  const columns = [
    {
      title: 'Done',
      dataIndex: 'check',
      align: 'center',
      
      //Ver ids de cada input
      render: (text, record) => (
        <div>
          <input type="checkbox" id="cbox1" value="first_checkbox" onClick={()=>setToDoStatus(record)}/> 
        </div>
      )
    },
    {
      title: 'Name',
      dataIndex: 'text',
      align: 'center',

      //CHECAR ESTO QUE NO FUNCIONA BIEN 
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
        compare: (a, b) => a.chinese - b.chinese,
        multiple: 3,
      },
    },
    {
      title: 'Due date',
      dataIndex: 'dueDate',
      align: 'center',

      sorter: {
        compare: (a, b) => a.math - b.math,
        multiple: 2,
      },
    },
    {
      title: 'Done date',
      dataIndex: 'doneDate',
      align: 'center',

      sorter: {
        compare: (a, b) => a.math - b.math,
        multiple: 2,
      },
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
    axios.delete(`http://localhost:8080/todos/${toDoId}`).then((response) => { 
      console.log(response.data);
      navigator('/');
  });
  }

  const onChange = (pagination, filters, sorter, extra) => {
    console.log('params', pagination, filters, sorter, extra);
  };
  
  

    useEffect(()=>{
      //axios.get('http://localhost:8080/todos').then((response)=>{
      axios.get(`http://localhost:8080/todos?text=${nameFilter}&priority=${filterPriority}&pagination=${pagination}`).then((response)=>{
      setData(response.data);
            
        }).catch(error =>{console.log(error);})
    }, [location]);
    
    return(
        <Table columns={columns} dataSource={data}  onChange={onChange} pagination={false}/>
    );
}


export default TableData;