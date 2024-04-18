import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import Table from 'react-bootstrap/Table';
import {useState} from 'react'

 
const customers = [
  { id: 1, name: "Khách hàng A", city: "Hà Nội" },
  { id: 2, name: "Khách hàng B", city: "TP. Hồ Chí Minh" },
  { id: 3, name: "Khách hàng C", city: "Đà Nẵng" },
  { id: 4, name: "Khách hàng D", city: "Nha Trang" },
  { id: 5, name: "Khách hàng E", city: "Hải Phòng" },
  { id: 6, name: "Khách hàng F", city: "Cần Thơ" },
];

function AddNewCustomer() {
  const [customer, setCustomer] = useState(customers ?? []);
  const [name, setName] = useState("");
  const [city, setCity] = useState("");

  const handleName = (e => setName(e.target.value));
  const handleCity = (e => setCity(e.target.value));

  const addCustomer = (e) => {
    e.preventDefault();
    const newCustomer = {
      id: customer.length + 1,
      name: name,
      city: city,
    };
    setCustomer([...customer, newCustomer]);
    e.target.reset();
  };
  console.log(customer);

  return (
    <Container>
      <Row>
        <Col>
          <center>Add New Customer</center>
          <Form onSubmit={addCustomer}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="name"
                placeholder="Enter name"
                onChange={handleName}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicPassword">
              <Form.Label>City</Form.Label>
              <Form.Control
                type="city"
                placeholder="Enter city"
                onChange={handleCity}
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
        </Col>
        <Col></Col>
        <Col></Col>
      </Row>
      <div>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>City</th>
            </tr>
          </thead>
          {customer.map((c) => (
            <tr>
              <td>{c.id}</td>
              <td>{c.name}</td>
              <td>{c.city}</td>
            </tr>
          ))}
        </Table>
      </div>
    </Container>
  );
}

export default AddNewCustomer;
