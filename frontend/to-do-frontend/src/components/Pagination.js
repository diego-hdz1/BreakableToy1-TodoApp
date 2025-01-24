import React from 'react';
import { Pagination } from 'antd';
import axios from "axios";
import { useNavigate } from 'react-router-dom';


function PaginationControll({nameFilter, filterPriority, filterDone, pagination, handlePagination,handleData, ordenation}){

  const navigator = useNavigate();
  function getData(){
        //let url = `http://localhost:8080/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}`;

        let url = `http://localhost:8080/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&pagination=${pagination}&orderPriority=${ordenation}`;
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
    if (pagination == 5) return;
    handlePagination(pagination+1);
    navigator("/");
  }

  //Aqui lo unico que debo de hacer es cambiar el estado de la pagination y capaz que hacer otro get para hacer el update de la data 
  //Cada vez que se pique un boton de paginacion es cuando se hace eso
  return(
    <div className='add-form' style={{backgroundColor : "darksalmon"}}>
      <button onClick={decPagination}>--</button>
      <h3>{pagination+1}</h3>
      <button onClick={incPagination}>++</button>
    </div>
  );
}


/*<>
    <Pagination className='pagination'
      simple={{
        readOnly: true,
      }}
      defaultCurrent={1}
      total={50}
    />
  </>); */


export default PaginationControll;