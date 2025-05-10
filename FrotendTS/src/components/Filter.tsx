import axios from "axios";
import { useEffect } from "react";
import {PORT} from '../constants';
import ModalComponent from "./Modal";
import { useState } from "react";


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
    fetchStats: () => void;
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
  dateSort,
  fetchStats
}) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const NO_ID_NEEDED = -1;

    function addNewToDo(e: React.MouseEvent<HTMLButtonElement>){
        e.preventDefault();    
        setIsModalOpen(true);
    }
    
    function dynamicFilter() {
        let validatedName = encodeURIComponent(nameFilter);
        let url = `http://localhost:${PORT}/todos?nameFilter=${validatedName}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
        axios.get(url)
            .then((response) => {
                setData(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    useEffect(() => {
        dynamicFilter();
    }, [nameFilter, filterPriority, filterDone, pagination, ordenation, dateSort]);
    
    return (
        <div> 
            <form className="add-form">
                <h3>Choose your options to filter</h3>
                <input type="text" placeholder="Name..." value={nameFilter} onChange={(e)=>handleNameFilter(e.target.value)} maxLength={120}></input>
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
            </form>
            <form className="add-form">
            <button onClick={addNewToDo}>Add To Do</button>
            </form>
            <ModalComponent currentId = {NO_ID_NEEDED} isModalOpen = {isModalOpen} fetchStats={fetchStats} setIsModalOpen={setIsModalOpen}/>
        </div>
    );
}

export default Filter;