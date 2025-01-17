import React, { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import axios from "axios";
//import listTodos from "../ToDoService.js";



const columns = [
  {
    title: 'Name',
    dataIndex: 'text',
    //dataIndex: 'name',
    //key:'name'
  },
  {
    title: 'Priority',
    dataIndex: 'priority',
    //dataIndex: 'chinese',
    //key:'priority',
    sorter: {
      compare: (a, b) => a.chinese - b.chinese,
      multiple: 3,
    },
  },
  {
    title: 'Due date',
    dataIndex: 'dueDate',
    //dataIndex: 'math',
    //key:'dueDate',
    sorter: {
      compare: (a, b) => a.math - b.math,
      multiple: 2,
    },
  },
  {
    title: 'Done date',
    dataIndex: 'doneDate',
    //dataIndex: 'english',
    //key:'doneDate',
    sorter: {
      compare: (a, b) => a.math - b.math,
      multiple: 2,
    },
  },
  {
    title: 'Actions',
    dataIndex: 'actions',
    //key:'actions',
    
    render: (text, record) => (
      <div>
        <Button type='primary' onClick={()=> console.log('Editar:', record)} style={{marginRight : 8}}>Editar</Button>
        <Button type='danger' onClick={()=> console.log('Eliminar:', record)} style={{marginRight : 8, backgroundColor: 'red', color : 'white'}}>Eliminar</Button>
      </div>
    )

  },
];
/*
const data = [
  {
    key: '1',
    name: 'John Brown',
    chinese: 98,
    math: 60,
    english: 70,
  },
  {
    key: '2',
    name: 'Jim Green',
    chinese: 98,
    math: 66,
    english: 89,
  },
  {
    key: '3',
    name: 'Joe Black',
    chinese: 98,
    math: 90,
    english: 70,
  },
  {
    key: '4',
    name: 'Jim Red',
    chinese: 88,
    math: 99,
    english: 89,
  },
  {
    key: '5',
    name: 'John Brown',
    chinese: 98,
    math: 60,
    english: 70,
  },
  {
    key: '6',
    name: 'Jim Green',
    chinese: 98,
    math: 66,
    english: 89,
  },
  {
    key: '7',
    name: 'Joe Black',
    chinese: 98,
    math: 90,
    english: 70,
  },
  {
    key: '8',
    name: 'Jim Red',
    chinese: 88,
    math: 99,
    english: 89,
  },
  {
    key: '9',
    name: 'Joe Black',
    chinese: 100,
    math: 90,
    english: 70,
  },
  {
    key: '10',
    name: 'Jim Red',
    chinese: 88,
    math: 99,
    english: 89,
  }
  
];
/*
const onChange = (pagination, filters, sorter, extra) => {
  console.log('params', pagination, filters, sorter, extra);
}; */


function TableData(){
  const onChange = (pagination, filters, sorter, extra) => {
    console.log('params', pagination, filters, sorter, extra);
  };
  
  const [data, setData] = useState([]);

    useEffect(()=>{
        axios.get('http://localhost:8080/todos').then((response)=>{
            setData(response.data);
        }).catch(error =>{console.log(error);})
    })
    

    //pagination={false}
    return(
        <Table columns={columns} dataSource={data}  onChange={onChange} />
    );
}


export default TableData;