import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
//import styles from "../ToDoData.module.css";

export default function ToDoData(){

    const [toDoName, setToDoName] = useState("");

    //LA DUE DATE PUEDE SER OPCIONAL AGREGAR ALGO PARA ESO
    const [toDoDueDate, setToDoDueDate] = useState("");
    const [toDoPriority, setToDoPriority] = useState(1);
    const navigator = useNavigate();
    let randomId;
    const {id} = useParams();

    function handleOnClick(e){
        e.preventDefault();
        const nowDate = new Date();
        let localDate = new Date(nowDate.getTime()-(nowDate.getTimezoneOffset() * 60000));
        const newToDo = {id: -1 ,text: toDoName, dueDate: toDoDueDate, status: true, doneDate: null, priority: toDoPriority, creationDate : localDate.toJSON()};
        axios.post('http://localhost:9090/todos', newToDo).then((response) => { 
            console.log(response.data);
            navigator('/');
        });
        
    }

    function updateToDo(e){
        e.preventDefault();
        const newToDo = {id: randomId ,text: toDoName, dueDate: toDoDueDate, status: true, doneDate: null, priority: toDoPriority};
        axios.put(`http://localhost:9090/todos/${id}`, newToDo).then((response) => { 
            console.log(response.data);
            navigator('/');
        });
    }    

    //ESTE GET NO SE MODIFICA PORQUE NO TIENE PARAMETROS
    useEffect(()=>{
        if(id){
            axios.get(`http://localhost:9090/todos/${id}`).then((response)=>{
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
        //className={styles.prueba}
        <div > 
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
                        onChange={(e)=>setToDoDueDate(e.target.value)}>
                        </input>
                        <button>ES OPCIONAL LA DUE DATE</button>
                    </div>

                    {/* <div>
                        <label>To do priority:</label>
                        <input
                        type="number" //DEBO DE CAMBIAR ESTO PARA QUE SEA HIGH, LOW, MEDIUM, ETC. POR AHORA ASI PQ LO TENGO EN LA API
                        placeholder="Enter the priority"
                        name="todopriority"
                        value={toDoPriority}
                        onChange={(e)=> setToDoPriority(e.target.value)}>
                        </input>
                    </div> */}

                    <div>
                        <label>To do priority:</label>
                        <select value={toDoPriority} onChange={(e)=>setToDoPriority(e.target.value)}>
                            <option value={1}>Low</option>
                            <option value={2}>Medium</option>
                            <option value={3}>High</option>
                        </select>
                    </div>
                    
                    <button onClick={id ? updateToDo : handleOnClick}>Submit</button>
                    <button onClick={()=>navigator('/')}>Cancel</button>
                </form>
            </div>
        </div>
    );

}