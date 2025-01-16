import TableData from "./components/Table";
import Header from "./components/Header";
import Filter from "./components/Filter";
import PaginationControll from "./components/Pagination";
import Stats from "./components/Stats";
import "./App.css";

function App(){
  return(
    <div>
      <Header />
      <br></br>
      <Filter />
      <TableData />
      <PaginationControll />
      <Stats />
    </div>
    
  );
}

export default App;