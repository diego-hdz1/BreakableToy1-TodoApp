import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function ToDoData(){

    const [toDoName, setToDoName] = useState("");
    const [toDoDueDate, setToDoDueDate] = useState("");
    //const [toDoDoneDate, setToDoDoneDate] = useState("");
    const [toDoPriority, setToDoPriority] = useState(0);
    const navigator = useNavigate();
    let randomId;

    function handleOnClick(e){
        e.preventDefault();
        randomId = Math.floor(Math.random() * (10000 - 100 + 1)) + 100;
        const newToDo = {id: randomId ,text: toDoName, dueDate: toDoDueDate, status: true, doneDate: "", priority: toDoPriority};
        axios.post('http://localhost:8080/todos', newToDo).then((response) => { 
            console.log(response.data);
            navigator('/');
        });
        
    }

    return (
        <div> 
            <h2>Add ToDo</h2>
            <div>
                <form>
                    <div>
                        <label>To do name:</label>
                        <input
                        type="text"
                        placeholder="Enter to do name"
                        name="todoname"
                        value={toDoName}
                        onChange={(e)=> setToDoName(e.target.value)}>
                        </input>
                    </div>

                    <div>
                        <label>To do due date:</label>
                        <input
                        type="date"
                        placeholder="Enter due date"
                        name="tododuedate"
                        value={toDoDueDate}
                        onChange={(e)=> setToDoDueDate(e.target.value)}>
                        </input>
                    </div>

                    <div>
                        <label>To do priority:</label>
                        <input
                        type="number" //DEBO DE CAMBIAR ESTO PARA QUE SEA HIGH, LOW, MEDIUM, ETC. POR AHORA ASI PQ LO TENGO EN LA API
                        placeholder="Enter the priority"
                        name="todopriority"
                        value={toDoPriority}
                        onChange={(e)=> setToDoPriority(e.target.value)}>
                        </input>
                    </div>
                    
                    <button onClick={handleOnClick}>Submit</button>
                </form>
            </div>
        </div>
    );

}