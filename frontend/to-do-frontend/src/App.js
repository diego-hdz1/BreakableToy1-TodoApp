import TableData from "./components/Table";
import Header from "./components/Header";
import Filter from "./components/Filter";
import PaginationControll from "./components/Pagination";
import Stats from "./components/Stats";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ToDoData from "./components/ToDoData";

function App(){
  return(
    <div>
      <BrowserRouter>
        <Header />
          <Routes>
            
            <Route path='/' element={ 
            <div> 
              <Filter /> 
              <TableData /> 
              <PaginationControll /> 
              <Stats />
            </div> }>
            
            </Route>
            <Route path='/add-todo' element={ <ToDoData /> }></Route>
            
          </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;