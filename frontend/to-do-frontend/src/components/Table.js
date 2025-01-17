import React, { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

function TableData(){

  function prueba(record){
    console.log(record)
  }

  const columns = [
    {
      title: 'Done',
      dataIndex: 'check',
      align: 'center',
      
      //Ver ids de cada input
      render: (text, record) => (
        <div>
          <input type="checkbox" id="cbox1" value="first_checkbox" onClick={()=>prueba(record)}/> 
        </div>
      )
    },
    {
      title: 'Name',
      dataIndex: 'text',
      align: 'center',

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
          <Button type='danger' onClick={()=> console.log('Eliminar:', record)} style={{marginRight : 8, backgroundColor: 'red', color : 'white'}}>Eliminar</Button>
        </div>
      )
  
    },
  ];
  const navigator = useNavigate();

  function updateToDo(toDoId){
    navigator(`/edit-todo/${toDoId}`);
  }

  const onChange = (pagination, filters, sorter, extra) => {
    console.log('params', pagination, filters, sorter, extra);
  };
  
  const [data, setData] = useState([]);

    useEffect(()=>{
        axios.get('http://localhost:8080/todos').then((response)=>{
            setData(response.data);
        }).catch(error =>{console.log(error);})
    })
    
    return(
        <Table columns={columns} dataSource={data}  onChange={onChange} pagination={false}/>
    );
}


export default TableData;