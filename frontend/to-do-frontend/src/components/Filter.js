import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Filter({nameFilter, filterPriority, filterDone, pagination, handleNameFilter, handleFilterPriority, handleFilterDone, setData}){

    const navigator = useNavigate();
    
    function addNewToDo(e){
        e.preventDefault();    
        navigator('/add-todo');
    }

    //Debo de obtener el get con todos los filtros y solo hago el update al setData con los resultados que obtenga de la API
    function handleFilter(e){
        e.preventDefault();
        let url;
        if(filterDone === "All"){
            url = `http://localhost:8080/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&pagination=${pagination}`;
        }else{
            url = `http://localhost:8080/todos?nameFilter=${nameFilter}&priorityFilter=${filterPriority}&filterDone=${filterDone}&pagination=${pagination}`;
        }
        axios.get(url).then((response)=>{
            setData(response.data);
        }).catch(error =>{console.log(error);})
        console.log(nameFilter, filterPriority, filterDone);
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
                    <option value={true}>Done</option>
                    <option value={false}>Undone</option>
                </select>
                <button onClick={handleFilter}>Filter</button>
            </form>
            
            <div>
                <form className="add-form">
                    <h3>Order by priority or due date: </h3>
                    <label
                    ><input type="checkbox" id="cbox1" value="first_checkbox" /> Order by priority</label
                    >

                    <input type="checkbox" id="cbox2" value="second_checkbox" />
                    <label for="cbox2">Order by due date</label>
                    <button>Order</button>
                </form>
                
            </div>
            <form className="add-form">
            <button onClick={addNewToDo}>Add To Do</button>
            </form>
        </div>
    );
}