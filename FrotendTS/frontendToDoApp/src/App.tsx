import TableData from "./components/Table";
import Header from "./components/Header";
import Filter from "./components/Filter";
import PaginationControll from "./components/Pagination";
import Stats from "./components/Stats";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ToDoData from "./components/ToDoData";
import {useEffect, useState } from "react";
import axios from "axios";


interface StatsData {
  averageTotalTime: number;
  averageLowTime: number;
  averageMediumTime: number;
  averageHighTime: number;
  numberPages: number;
}


function App(){

  const [data, setData] =  useState<any[]>([]);
  const [nameFilter, setNameFilter] = useState<string>("");
  const [filterPriority, setFilterPriority] = useState<number>(0);
  const [filterDone, setFilterDone] = useState<string>("All");
  const [pagination, setPagination] = useState<number>(0);
  const [ordenation, setOrdenation] = useState<number>(1);
  const [dateSort, setDateSort] = useState<number>(1);
  const [stats, setStats] = useState<StatsData | null>(null);


  const fetchStats = async() => {
    try{
      const response = await axios.get<StatsData>("http://localhost:9090/todos/stats");
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
              <Filter 
              nameFilter={nameFilter} 
              filterPriority={filterPriority} 
              filterDone={filterDone} 
              pagination={pagination}
              handleNameFilter={setNameFilter} 
              handleFilterPriority={setFilterPriority} 
              handleFilterDone={setFilterDone} 
              setData = {setData}
              ordenation={ordenation} 
              dateSort = {dateSort} 
              /> 
              
              <TableData data={data} setData={setData} 
              nameFilter={nameFilter} filterPriority={filterPriority} filterDone={filterDone} pagination={pagination} 
              ordenation={ordenation} handleOrdenation = {setOrdenation} dateSort={dateSort} handleDateSort={setDateSort}
              fetchStats = {fetchStats}
              /> 

              <PaginationControll 
              pagination={pagination} 
              handlePagination={setPagination} 
              handleData={setData} 
              stats={stats || {numberPages : 0}}
              nameFilter={nameFilter} 
              filterPriority={filterPriority}
              filterDone={filterDone}
              ordenation={ordenation} 
              dateSort={dateSort} 
              /> 

              {stats && <Stats stats={stats} setStats={setStats}/>}
              
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