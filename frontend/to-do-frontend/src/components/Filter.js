import { useNavigate } from "react-router-dom";

export default function Filter(){

    const navigator = useNavigate();

    function addNewToDo(e){
        e.preventDefault();
        
        navigator('/add-todo');

    }
    
    return (
        <div> 
            
            <form className="add-form">
                <h3>Choose your options to filter</h3>
                <input type="text" placeholder="Name..."></input>
                <select>
                    <option>All</option>
                    <option>Low</option>
                    <option>Medium</option>
                    <option>High</option>
                </select>
                <select>
                    <option>All</option>
                    <option>Done</option>
                    <option>Undone</option>
                </select>
                <button>Filter</button>
            </form>
            <form className="add-form">
            <button onClick={addNewToDo}>Add To Do</button>
            </form>
        </div>
    );
}