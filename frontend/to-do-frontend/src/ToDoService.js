import axios from "axios";

const REST_API_URL = 'http://localhost:8080/todos'

export const listTodos = () => axios.get(REST_API_URL);