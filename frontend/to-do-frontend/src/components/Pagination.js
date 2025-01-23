import React from 'react';
import { Pagination } from 'antd';
import axios from "axios";
import { useNavigate } from 'react-router-dom';


function PaginationControll({nameFilter, filterPriority, filterDone, pagination, handlePagination,handleData, ordenation, dateSort, stats}){

  const navigator = useNavigate();
  function getData(){
        let url = `http://localhost:9090/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
        axios.get(url).then((response)=>{
            handleData(response.data);
        }).catch(error =>{console.log(error);})
        console.log(nameFilter, filterPriority, filterDone);
  }

  function decPagination(){
    getData();
    if (pagination == 0) return;
    handlePagination(pagination-1);
    navigator("/");
  }

  function incPagination(){
    //El maximo va a estar dado por los stats 
    getData();
    if (stats.numberPages<=pagination) return;
    handlePagination(pagination+1);
    navigator("/");
  }

  //Aqui lo unico que debo de hacer es cambiar el estado de la pagination y capaz que hacer otro get para hacer el update de la data 
  //Cada vez que se pique un boton de paginacion es cuando se hace eso
  return(
    <div className='add-form' style={{backgroundColor : "#e5771f"}}>
      <button onClick={decPagination}>&larr;</button>
      <h3 >{pagination+1}</h3>
      <button onClick={incPagination}>&rarr;</button>
    </div>
  );
}


export default PaginationControll;