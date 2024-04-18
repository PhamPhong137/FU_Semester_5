import axios from "axios";
import React, { useEffect, useState } from "react";
import { Form, Col, Row, Button, Table } from "react-bootstrap";

function Index() {
  const [users, setUser] = useState([]);
  const [todos, setTodo] = useState([]);
  const [selectedUserIds, setSelectedUserIds] = useState([]);
  const [filterTodo, setfilterTodo] = useState([]);
  const [sort, setSort] = useState(true);
  const [radio, setRadio] = useState(null);

  useEffect(() => {
    fetchUsers();
    fetchTodo();
  }, []);

  const fetchUsers = async () => {
    const response = await axios.get("http://localhost:9999/user");
    setUser(response.data);
  };

  const fetchTodo = async () => {
    const response = await axios.get("http://localhost:9999/todo");
    setTodo(response.data);
  };
  const getUsernameByTodo = (userId) => {
    const user = users.find((user) => user.id === userId);
    return user ? user.name : "Unknown User";
  };

  const handleUserSelectionChange = (userId, isChecked) => {
    if (isChecked) {
      setSelectedUserIds([...selectedUserIds, userId]);
    } else {
      setSelectedUserIds(selectedUserIds.filter((id) => id !== userId));
    }
  };
  useEffect(() => {
    if (selectedUserIds.length > 0) {
      setfilterTodo(
        todos.filter(
          (todo) =>
            selectedUserIds.includes(todo.userId) &&
            (radio==null ||
            todo.completed == radio)
        )
      );
    } else {
      setfilterTodo(todos);
    }
  }, [selectedUserIds, todos, radio]);

  console.log(radio);
  const filterTodoByAsc = () => {
    setfilterTodo((prevFilterTodo) => {
      let sortedTodos = [];
      if (sort) {
        sortedTodos = [...prevFilterTodo].sort((a, b) =>
          a.title.localeCompare(b.title)
        );
        setSort(false);
      } else {
        sortedTodos = [...prevFilterTodo].sort((a, b) =>
          b.title.localeCompare(a.title)
        );
        setSort(true);
      }
      return sortedTodos;
    });
  };

  const changeComplete = async (id, status) => {
    const changeStatus = !status;

    await axios.patch(`http://localhost:9999/todo/${id}`, {
      completed: changeStatus,
    });
    alert("Changed sucessfully!");
    fetchTodo();
  };

  return (
    <div>
      <Row>
        <Col sm={7}>
          <h1>Todo List</h1>
          Sort:{" "}
          <Button
            type="button"
            onClick={() => filterTodoByAsc()}
            variant="primary"
          >
            Ascending by Title
          </Button>
          <br />
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>No.</th>
                <th>Title</th>
                <th>User</th>
                <th>Completed</th>
                <th>Change status</th>
              </tr>
            </thead>
            <tbody>
              {filterTodo.map((todo) => (
                <tr>
                  <td>{todo.id}</td>
                  <td>{todo.title}</td>
                  <td>{getUsernameByTodo(todo.userId)}</td>
                  {todo.completed ? (
                    <td style={{ color: "blue" }}>Finished</td>
                  ) : (
                    <td style={{ color: "red" }}>Unfinished</td>
                  )}

                  <td>
                    <Button
                      type="button"
                      onClick={() => changeComplete(todo.id, todo.completed)}
                      variant="success"
                    >
                      Change
                    </Button>{" "}
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
        <Col sm={5}>
          <h3>Users</h3>
          {users.map((user) => (
            <Form.Check
              aria-label="option 1"
              label={user.name}
              onChange={(e) =>
                handleUserSelectionChange(user.id, e.target.checked)
              }
            />
          ))}
          <h3>Completed</h3>
          <Form.Check
            name="radio"
            type="radio"
            aria-label="radio 1"
            label="Finished"
            onChange={() => setRadio(true)}
          />
          <Form.Check
            name="radio"
            type="radio"
            aria-label="radio 1"
            label="Unfinished"
            onChange={() => setRadio(false)}
          />
          <Form.Check
            name="radio"
            type="radio"
            aria-label="radio 1"
            label="All"
            onChange={() => setRadio(null)}
          />
        </Col>
      </Row>
    </div>
  );
}

export default Index;
