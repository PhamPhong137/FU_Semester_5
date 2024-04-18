import React, { useState,useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Form } from "react-bootstrap";
import axios from "axios";

function AddProduct() {
  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState("");

  const [users, setUser] = useState([]);
  const [isLogin, setIsLogin] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    fetchUser();
  }, []);

  const fetchUser = async () => {
    const response = await axios.get("http://localhost:3000/users");
    setUser(response.data);
  };
  const createProduct = async () => {
    const product = { id, name, categoryId, price, description };
    await axios.post("http://localhost:3000/products", product);
    setId("");
    setName("");
    setCategoryId("");
    setPrice("");
    setDescription("");
  };
  const handleLogin = () => {
    console.log(email);
    console.log(password);

    const user = users.find(
      (user) => user.email === email && user.password === password
    );
    console.log(user);
    if (user) {
      setIsLogin(true);
    } else {
      alert("Incorrect account");
    }
  };
  const handleLogout = () => {
    setIsLogin(false);
  };

  return (
    <div style={{ width: "580px", margin: "auto" }}>
      <h3>Input account in the user array to add product</h3>
      {!isLogin ? (
        <div>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Gmail</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter gmail"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </Form.Group>

            <Button variant="primary" onClick={handleLogin}>
              Login
            </Button>
          </Form>
        </div>
      ) : (
        <>
          <Button variant="danger" onClick={handleLogout}>
            Logout
          </Button>
          <br />
        </>
      )}
      {isLogin ? (
        <>        
        <h1 style={{ textAlign: "center" }}>Add product</h1>
        <Form style={{ width: "400px", margin: "0 auto" }}>
        <Form.Group className="mb-3">
          <Form.Label>Id</Form.Label>
          <Form.Control
            type="text"
            value={id}
            onChange={(e) => setId(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>CategoryId</Form.Label>
          <Form.Control
            type="text"
            value={categoryId}
            onChange={(e) => setCategoryId(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="text"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Description</Form.Label>
          <Form.Control
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </Form.Group>
        <Button onClick={createProduct}>Add Product</Button>
      </Form>
        </>
        
      ):""
      }
      
    </div>
  );
}

export default AddProduct;
