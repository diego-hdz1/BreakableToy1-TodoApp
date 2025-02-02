import React, { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import type {TableColumnsType, TableProps} from 'antd';
import axios from "axios";
import { To, useLocation, useNavigate } from 'react-router-dom';
import {PORT} from '../constants';
import { Color } from 'antd/es/color-picker';

interface ToDo{
  id: number;
  text: string;
  priority: number;
  dueDate: string;    
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

    const goodLookingDate = (dueDate:string):any =>{
      const givenDueDate: any = new Date(dueDate);
      const currentDate: any = new Date();
      if(dueDate === "" || dueDate == null){
        return {backgroundColor:"white"}
      }
      const finalDate: number = givenDueDate - currentDate ;
      const finalDateToDays: number = finalDate / 1000 / 60 / 60 / 24;
      if(finalDateToDays < 0){ //Already due date is passed
        return {backgroundColor:"#455A64"}
      }
      else if(finalDateToDays >= 0 && finalDateToDays <7){
        return {backgroundColor:"#F28B82"}
      }
      else if(finalDateToDays >= 7 && finalDateToDays <14){
        return {backgroundColor:"#F6BF82"}
      }
      else if(finalDateToDays >= 14){
        return {backgroundColor:"#A8D5BA"}
      }
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
          //Just to get the "click" message when hovering in the header
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
          //Just to get the "click" message when hovering in the header
        },
        onCell: (record:ToDo) =>({
          style: goodLookingDate(record.dueDate),
        }),
        render: (text:string, record:ToDo) => (
          <span>
          {text !== null ? text.substring(0,10) : ""}
          </span>
        ),
        onHeaderCell: () => ({
          onClick: () => handleDate()
        }),
      },
      {
        title: 'Actions',
        dataIndex: 'actions',
        align: 'center',
        
        render: (_:any, record:ToDo) => (
          <>
          {record.status === true ? (
          <div>
            <Button type='primary' onClick={()=> updateToDo(record.id)} style={{marginRight : 8}}>Edit</Button>
            <Button type='primary' danger onClick={()=> deleteToDo(record.id)} style={{marginRight : 8, backgroundColor: 'red', color : 'white'}}>Delete</Button>
          </div>) : (<span></span>)}
          </>
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