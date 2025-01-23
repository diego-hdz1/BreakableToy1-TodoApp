import TableData from "./components/Table";
import Header from "./components/Header";
import Filter from "./components/Filter";
import PaginationControll from "./components/Pagination";
import Stats from "./components/Stats";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ToDoData from "./components/ToDoData";
import { use, useEffect, useState } from "react";
import axios from "axios";


function App(){

  const [data, setData] =  useState([]);
  const [nameFilter, setNameFilter] = useState("");
  const [filterPriority, setFilterPriority] = useState(0);
  const [filterDone, setFilterDone] = useState("All");
  const [pagination, setPagination] = useState(0);
  const [ordenation, setOrdenation] = useState(1);
  const [dateSort, setDateSort] = useState(1);
  const [stats, setStats] = useState("");


  const fetchStats = async() => {
    try{
      const response = await axios.get("http://localhost:9090/todos/stats");
      setStats(response.data);
    } catch(error){
      console.log("Error fetching stats", error);
    }
  }

  useEffect(()=>{
    fetchStats();
  }, []);

  return(
    <div>
      <BrowserRouter>
        <Header />
          <Routes>
            
            <Route path='/' element={ 
            <div> 
              <Filter nameFilter={nameFilter} filterPriority={filterPriority} filterDone={filterDone} pagination={pagination}
              handleNameFilter={setNameFilter} handleFilterPriority={setFilterPriority} handleFilterDone={setFilterDone} setData = {setData}
              ordenation={ordenation} handleOrdenation = {setOrdenation} dateSort = {dateSort} handleDateSort={setDateSort}
              /> 
              <TableData data={data} setData={setData} 
              nameFilter={nameFilter} filterPriority={filterPriority} filterDone={filterDone} pagination={pagination} 
              ordenation={ordenation} handleOrdenation = {setOrdenation} dateSort={dateSort} handleDateSort={setDateSort}
              fetchStats = {fetchStats}
              /> 
              <PaginationControll pagination={pagination} handlePagination={setPagination} handleData={setData} stats={stats}
              nameFilter={nameFilter} filterPriority={filterPriority} filterDone={filterDone} ordenation={ordenation} dateSort={dateSort} handleDateSort={setDateSort}
              /> 

              <Stats stats={stats}/>
              
            </div> }>
            
            </Route>
            <Route path='/add-todo' element={ <ToDoData /> }></Route>
            <Route path='/edit-todo/:id' element={ <ToDoData /> }></Route>
            {/* <Route path='/todos/stats' element={ <Stats stats={stats} handleStats = {setStats}
              /> }></Route> */}
          </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;