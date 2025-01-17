import axios from "axios";

const REST_API_URL = 'http://localhost:8080/todos'

export const listTodos = () => axios.get(REST_API_URL);

export const createToDo = (toDo) => axios.post(REST_API_URL, toDo);