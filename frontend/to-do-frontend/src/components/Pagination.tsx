import React from 'react';
import { Pagination } from 'antd';
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import {PORT} from "../ToDoService";

interface PaginationControllProps{
  nameFilter: string;
  filterPriority: number;
  filterDone: string;
  pagination: number;
  handlePagination: (newPage : number) => void;
  handleData: (data: any) => void;
  ordenation: number;
  dateSort: number;
  stats:{
    numberPages: number;
  }
}

const PaginationControll: React.FC<PaginationControllProps> = ({
  nameFilter,
  filterPriority,
  filterDone,
  pagination,
  handlePagination,
  handleData,
  ordenation,
  dateSort,
  stats
}) => {
  const navigator = useNavigate();

  function getData(){
    let url = `http://localhost:${PORT}/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
    axios.get(url).then((response)=>{
        handleData(response.data);
    }).catch(error =>{console.log(error);})
  }

    function decPagination(){
      getData();
      if (pagination == 0) return;
      handlePagination(pagination-1);
      navigator("/");
    }
  
    function incPagination(){
      getData();
      if (stats.numberPages<=pagination) return;
      handlePagination(pagination+1);
      navigator("/");
    }

    return(
      <div className='add-form' style={{backgroundColor : "#e5771f"}}>
        <button onClick={decPagination}>&larr;</button>
        <h3 >{pagination+1}</h3>
        <button onClick={incPagination}>&rarr;</button>
      </div>
    );
};

export default PaginationControll;