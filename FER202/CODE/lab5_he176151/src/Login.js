import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import axios from "axios";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const isLoggedIn = localStorage.getItem("isLoggedIn");
    if (isLoggedIn === "true") {
      navigate("/orders");
    }
  }, [navigate]);

  const handleLogin = async () => {
    const response = await axios.get("http://localhost:3000/customers");
    const customer = response.data.find(c => c.email === email && c.password === password);
    if (customer) {
      localStorage.setItem("isLoggedIn", "true");
      navigate("/orders"); 
    } else {
      alert("Incorrect account");
    }
  };

  return (
    <Form style={{ width: "50%", margin: "auto", marginTop: "10px" }}>
      <Form.Group className="mb-3">
        <Form.Label>Email</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
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
  );
}

export default Login;
