import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

export default function ToDoData(){

    const [toDoName, setToDoName] = useState("");
    const [toDoDueDate, setToDoDueDate] = useState("");
    const [toDoPriority, setToDoPriority] = useState(0);
    const navigator = useNavigate();
    let randomId;
    const {id} = useParams();

    function handleOnClick(e){
        e.preventDefault();
        randomId = Math.floor(Math.random() * (10000 - 100 + 1)) + 100;
        const newToDo = {id: randomId ,text: toDoName, dueDate: toDoDueDate, status: true, doneDate: "", priority: toDoPriority};
        axios.post('http://localhost:8080/todos', newToDo).then((response) => { 
            console.log(response.data);
            navigator('/');
        });
        
    }

    function updateToDo(e){
        e.preventDefault();
        const newToDo = {id: randomId ,text: toDoName, dueDate: toDoDueDate, status: true, doneDate: "", priority: toDoPriority};
        axios.put(`http://localhost:8080/todos/${id}`, newToDo).then((response) => { 
            console.log(response.data);
            navigator('/');
        });
    }
    

    //Modificar para que se pueda editar datos, porque cada vez que se edita se vuelve a poner los mismos datos otra vez porque vuelve a llamar a la API
    useEffect(()=>{
        if(id){
            axios.get(`http://localhost:8080/todos/${id}`).then((response)=>{
                setToDoName(response.data.text);
                setToDoDueDate(response.data.dueDate);
                setToDoPriority(response.data.priority);
            }).catch(error =>{console.log(error);})
        }
    }, [id])

    function Title(){
        if (id){
            return <h2>Update ToDo</h2>
        }
        return <h2>Add ToDo</h2>
    }

    return (
        <div> 
            {Title()}
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
                        min={new Date().toISOString().split("T")[0]}
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
                    
                    <button onClick={id ? updateToDo : handleOnClick}>Submit</button>
                    <button onClick={()=>navigator('/')}>Cancel</button>
                </form>
            </div>
        </div>
    );

}