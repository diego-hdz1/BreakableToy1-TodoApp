import TableData from "./components/Table";
import Header from "./components/Header";
import Filter from "./components/Filter";
import PaginationControll from "./components/Pagination";
import Stats from "./components/Stats";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ToDoData from "./components/ToDoData";
import { useState } from "react";

function App(){

  const [data, setData] =  useState([]);
  const [nameFilter, setNameFilter] = useState("");
  const [filterPriority, setFilterPriority] = useState(0);
  const [filterDone, setFilterDone] = useState(null);
  const [pagination, setPagination] = useState(0);
  const [ordenation, setOrdenation] = useState("");


  //PARA LAS STATS PUEDE SER UN NUEVO OBJETO Y PUEDE LLEVAR UN PARAMETRO DE CUANTAS PAGINAS HAY EN LA LISTA PARA MANDARLAS AL PAGINATION!!

  return(
    <div>
      <BrowserRouter>
        <Header />
          <Routes>
            
            <Route path='/' element={ 
            <div> 
              <Filter nameFilter={nameFilter} filterPriority={filterPriority} filterDone={filterDone} pagination={pagination}
              handleNameFilter={setNameFilter} handleFilterPriority={setFilterPriority} handleFilterDone={setFilterDone} setData = {setData}
              /> 
              <TableData data={data} setData={setData}
              nameFilter={nameFilter} filterPriority={filterPriority} filterDone={filterDone} pagination={pagination}
              /> 
              <PaginationControll pagination={pagination} handlePagination={setPagination} handleData={setData} 
              nameFilter={nameFilter} filterPriority={filterPriority} filterDone={filterDone}
              /> 

            
              <Stats />
            </div> }>
            
            </Route>
            <Route path='/add-todo' element={ <ToDoData /> }></Route>
            <Route path='/edit-todo/:id' element={ <ToDoData /> }></Route>
          </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;