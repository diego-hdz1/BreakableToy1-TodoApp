import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Filter({nameFilter, filterPriority, filterDone, pagination, handleNameFilter, handleFilterPriority, handleFilterDone, setData, ordenation, dateSort}){

    const navigator = useNavigate();
    
    function addNewToDo(e){
        e.preventDefault();    
        navigator('/add-todo');
    }

    function handleFilter(e){
        e.preventDefault();
        let url = `http://localhost:9090/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}&orderPriority=${ordenation}&orderDate=${dateSort}`;
        axios.get(url).then((response)=>{
            setData(response.data);
        }).catch(error =>{console.log(error);})
    }
    
    return (
        <div> 
            
            <form className="add-form">
                <h3>Choose your options to filter</h3>
                <input type="text" placeholder="Name..." value={nameFilter} onChange={(e)=>handleNameFilter(e.target.value)}></input>
                <select value={filterPriority} onChange={(e)=>handleFilterPriority(e.target.value)}>
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