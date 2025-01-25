import { useNavigate } from "react-router-dom";
import axios from "axios";
import {PORT} from '../constants';

interface FilterProps{
    nameFilter: string;
    filterPriority: number;
    filterDone: string;
    pagination: number;
    handleNameFilter: (value : string) => void;
    handleFilterPriority: (value: number) => void;
    handleFilterDone: (value: string) => void;
    setData: (data:any) => void;
    ordenation: number;
    dateSort: number;
}

const Filter: React.FC<FilterProps> = ({
  nameFilter,
  filterPriority,
  filterDone,
  pagination,
  handleNameFilter,
  handleFilterPriority,
  handleFilterDone,
  setData,
  ordenation,
  dateSort
}) => {
    const navigator = useNavigate();

    function addNewToDo(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault();    
        navigator('/add-todo');
    }

    function handleFilter(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault();
        let url = `http://localhost:${PORT}/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
        axios.get(url).then((response)=>{
            setData(response.data);
        }).catch(error =>{console.log(error);})
    }

    return (
        <div> 
            
            <form className="add-form">
                <h3>Choose your options to filter</h3>
                <input type="text" placeholder="Name..." value={nameFilter} onChange={(e)=>handleNameFilter(e.target.value)}></input>
                <select value={filterPriority} onChange={(e)=>handleFilterPriority(Number(e.target.value))}>
                    <option value={0}>All</option>
                    <option value={1}>Low</option>
                    <option value={2}>Medium</option>
                    <option value={3}>High</option>
                </select>
                <select value={filterDone} onChange={(e)=>handleFilterDone(e.target.value)}>
                    <option value={"All"}>All</option>
                    <option value={"Done"}>Done</option>
                    <option value={"Undone"}>Undone</option>
                </select>
                <button onClick={handleFilter}>Filter</button>
            </form>
            <form className="add-form">
            <button onClick={addNewToDo}>Add To Do</button>
            </form>
        </div>
    );

}

export default Filter;