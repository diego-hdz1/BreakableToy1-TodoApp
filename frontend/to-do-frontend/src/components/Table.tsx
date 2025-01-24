import React, { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import type {TableColumnsType, TableProps} from 'antd';
import axios from "axios";
import { To, useLocation, useNavigate } from 'react-router-dom';
import {PORT} from "../ToDoService";

interface ToDo{
  id: number;
  text: string;
  priority: number;
  dueDate: string;    //Checar el ? al final de la variable
  doneDate: string;
  creationDate: string;
  status: boolean;
}

interface TableDataProps{
  data: ToDo[];
  setData: React.Dispatch<React.SetStateAction<ToDo[]>>;
  nameFilter: string;
  filterPriority: number;
  filterDone: string;
  pagination: number;
  ordenation: number;
  handleOrdenation: (value:number) => void;
  dateSort: number;
  handleDateSort: (value:number) => void;
  fetchStats: () => void;
}

const TableData: React.FC<TableDataProps> = ({
  data,
  setData,
  nameFilter,
  filterPriority,
  filterDone,
  pagination,
  ordenation,
  handleOrdenation,
  dateSort,
  handleDateSort,
  fetchStats,
}) => {

  const navigator = useNavigate();
  const location = useLocation();
  
  const setToDoStatus = (record: ToDo) =>{
    const url = record.status ? `http://localhost:${PORT}/todos/${record.id}/done` : `http://localhost:9090/todos/${record.id}/undone`;
    axios.put(url).then((response) => { 
      fetchStats();
      navigator('/');
    });
  }

  const handlePriority = () => {
    if(ordenation === 1){
      handleOrdenation(ordenation + 1);
    }else if(ordenation === 2){
      handleOrdenation(ordenation + 1);
    }else if(ordenation === 3){
      handleOrdenation(1);
    }
    fetchData();
    navigator('/');
  }

  const handleDate = () =>{
    if(dateSort === 1){
      handleDateSort(dateSort + 1);
    }else if(dateSort === 2){
      handleDateSort(dateSort + 1);
    }else if(dateSort === 3){
      handleDateSort(1);
    }
    fetchData();
    navigator('/');
  }

  const fetchData = () => {
    const url = `http://localhost:${PORT}/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
    axios.get(url).then((response)=>{
      setData(response.data);
    }).catch(error =>{console.log(error);})
    }      

    const updateToDo = (toDoId:number)=>{
      navigator(`/edit-todo/${toDoId}`);
    }
  
    const deleteToDo = (toDoId:number)=>{
      axios.delete(`http://localhost:${PORT}/todos/${toDoId}`).then((response) => { 
        fetchStats();
        navigator('/');
    });
    }
  

    const columns: any[] = [
      {
        title: 'Done',
        dataIndex: 'check',
        align: 'center',
        
        render: (_:any, record: ToDo) => (
          <input type="checkbox" defaultChecked={!record.status} onClick={()=>setToDoStatus(record)}/> 
        )
      },
      {
        title: 'Name',
        dataIndex: 'text',
        align: 'center',
        render: (text:string, record:ToDo) => (
          <span style={{textDecoration : !record.status ? 'line-through' : 'none', textDecorationThickness : '2px'}}>
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
        render: (text:number, record:ToDo) => (
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
        render: (text:string, record:ToDo) => (
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
        
        render: (_:any, record:ToDo) => (
          <div>
            <Button type='primary' onClick={()=> updateToDo(record.id)} style={{marginRight : 8}}>Editar</Button>
            <Button type='primary' danger onClick={()=> deleteToDo(record.id)} style={{marginRight : 8, backgroundColor: 'red', color : 'white'}}>Eliminar</Button>
          </div>
        )
    
      },
    ];

    useEffect(()=>{
      if(location.pathname === "/"){
      fetchData();}
    }, [location]);
    
    return(
        <Table<ToDo> columns={columns} dataSource={data} pagination={false} rowKey={(record)=> record.id.toString()}/>
    );

}

export default TableData;