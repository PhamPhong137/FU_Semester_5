import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import AddNewCustomer from "./AddNewCustomer";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Table from "react-bootstrap/Table";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Dropdown from "react-bootstrap/Dropdown";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import FormControl from "react-bootstrap/FormControl";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import { useState } from "react";

const customers = [
  { id: 1, name: "Khách hàng A", city: "Hà Nội" },
  { id: 2, name: "Khách hàng B", city: "TP. Hồ Chí Minh" },
  { id: 3, name: "Khách hàng C", city: "Đà Nẵng" },
  { id: 4, name: "Khách hàng D", city: "Nha Trang" },
  { id: 5, name: "Khách hàng E", city: "Hải Phòng" },
  { id: 6, name: "Khách hàng F", city: "Cần Thơ" },
];
const orders = [
  { id: 1, customerId: 1, productId: 101, quantity: 2 },
  { id: 2, customerId: 2, productId: 103, quantity: 1 },
  { id: 3, customerId: 3, productId: 102, quantity: 4 },
  { id: 4, customerId: 4, productId: 104, quantity: 1 },
  { id: 5, customerId: 5, productId: 105, quantity: 3 },
  { id: 6, customerId: 6, productId: 101, quantity: 2 },
  { id: 7, customerId: 3, productId: 106, quantity: 1 },
  { id: 8, customerId: 1, productId: 104, quantity: 2 },
  { id: 9, customerId: 4, productId: 102, quantity: 3 },
  { id: 10, customerId: 2, productId: 105, quantity: 1 },
  { id: 11, customerId: 5, productId: 103, quantity: 2 },
  { id: 12, customerId: 6, productId: 101, quantity: 1 },
];
const products = [
  { id: 101, name: "Sản phẩm 1", price: 100, imageUrl: "/images/event-1.jpg" },
  { id: 102, name: "Sản phẩm 2", price: 200, imageUrl: "/images/event-2.jpg" },
  { id: 103, name: "Sản phẩm 3", price: 150, imageUrl: "/images/event-3.jpg" },
  { id: 104, name: "Sản phẩm 4", price: 250, imageUrl: "/images/event-4.jpg" },
  { id: 105, name: "Sản phẩm 5", price: 300, imageUrl: "/images/event-5.jpg" },
  { id: 106, name: "Sản phẩm 6", price: 350, imageUrl: "/images/event-6.jpg" },
];

function c3(customers, orders) {
  return (
    <Tabs defaultActiveKey="0" id="customer-orders-tabs" className="mb-3">
      {customers.map((customer, index) => (
        <Tab eventKey={index} title={customer.name} key={customer.id}>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Order ID</th>
                <th>Product ID</th>
                <th>Quantity</th>
              </tr>
            </thead>
            <tbody>
              {orders
                .filter((order) => order.customerId === customer.id)
                .map((order) => (
                  <tr>
                    <td>{order.id}</td>
                    <td>{order.productId}</td>
                    <td>{order.quantity}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Tab>
      ))}
    </Tabs>
  );
}

function C4(orders) {
  const [show, setShow] = useState(false);
  const [curOrder, setCurOrder] = useState(false);

  const handleClose = () => {
    setShow(false);
    setCurOrder(false);
  };

  const handleShow = (order) => {
    setCurOrder(order);
    setShow(true);
  };

  return (
    <>
      {orders.map((order) => (
        <div key={order.id}>
          <Button variant="primary" onClick={() => handleShow(order)}>
            OrderID: {order.id}
          </Button>
        </div>
      ))}

      {curOrder && (
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>CustomerID: {curOrder.customerId}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            ProductId: {curOrder.productId} Quantity: {curOrder.quantity}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      )}
    </>
  );
}

function C5(products) {
  const [filterProduct, setFilterProducts] = useState(products);

  const filterByPrice = (price) => {
    const filtered = products.filter((product) => product.price === price);
    setFilterProducts(filtered);
  };

  return (
    <div className="mt-5">
      <Dropdown>
        <Dropdown.Toggle variant="primary" id="dropdown-basic">
          Filer By Price
        </Dropdown.Toggle>
        <Dropdown.Menu>
          {products.map((product) => (
            <Dropdown.Item onClick={() => filterByPrice(product.price)}>
              {product.price}
            </Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>

      <div className="product-list" style={{ display: "flex", gap: "20px" }}>
        {filterProduct.map((product) => (
          <div key={product.id} className="product-item">
            <h3>{product.name}</h3>
            <p>${product.price}</p>
            <img
              src={product.imageUrl}
              alt={product.name}
              style={{ width: "100px", height: "100px" }}
            />
          </div>
        ))}
      </div>
    </div>
  );
}

function c6(products) {
  return (
    <div className="mt-5">
      <Row>
        {products.map((product) => (
          <Col md={2}>
            <Card style={{ width: "20rem" }}>
              <Card.Img variant="top" src={product.imageUrl} />
              <Card.Body>
                <Card.Title>{product.name}</Card.Title>
                <Card.Text>ID: {product.id}</Card.Text>
                <Card.Text>Price: {product.price}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
}
function c10(customers, orders, products) {
  return (
    <div className="mt-5">
      <h3>Customer</h3>
      <Row>
        {customers.map((customer) => (
          <Col md={2}>
            <Card style={{ width: "20rem" }}>
              <Card.Body>
                <Card.Title>ID: {customer.id}</Card.Title>
                <Card.Text>Name: {customer.name}</Card.Text>
                <Card.Text>City: {customer.city}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
      <h3>Order</h3>
      <Row>
        {orders.map((order) => (
          <Col md={2}>
            <Card style={{ width: "20rem" }}>
              <Card.Body>
                <Card.Title>ID: {order.id}</Card.Title>
                <Card.Text>customerId: {order.customerId}</Card.Text>
                <Card.Text>productId: {order.productId}</Card.Text>
                <Card.Text>quantity: {order.quantity}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
      <h3>Product</h3>
      <Row>
        {products.map((product) => (
          <Col md={2}>
            <Card style={{ width: "20rem" }}>
              <Card.Img variant="top" src={product.imageUrl} />
              <Card.Body>
                <Card.Title>{product.name}</Card.Title>
                <Card.Text>ID: {product.id}</Card.Text>
                <Card.Text>Price: {product.price}</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
}

function C8(customers) {
  const [filteredCustomer, setFilteredCustomers] = useState([]);
  const filterByNameOrCity = (value) => {
    const lower = value.toLowerCase();
    const filterCustomer = customers.filter(
      (customer) =>
        customer.name.toLowerCase().includes(lower) ||
        customer.city.toLowerCase().includes(lower)
    );
    setFilteredCustomers(filterCustomer);
  };
  return (
    <div>
      <Form className="d-flex ">
        <FormControl
          style={{ width: 400 }}
          type="text"
          placeholder="Search by name or city"
          className="me-2"
          aria-label="Search"
          onChange={(e) => filterByNameOrCity(e.target.value)}
        />
      </Form>
      <ul>
        {filteredCustomer.map((customer) => (
          <li key={customer.id}>
            {customer.name} - {customer.city}
          </li>
        ))}
      </ul>
    </div>
  );
}

function c7(customers, orders, products) {
  // const a =orders.filter(order => order.customerId===customer.id);
  // a.map(order => products.filter(product.id= order.productId)).reduce((a,b)=> a+ b.price);
  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>Customer</th>
          <th>Number of orders</th>
          <th>Total value</th>
        </tr>
      </thead>
      <tbody>
        {customers.map((customer) => (
          <tr>
            <td>{customer.id}</td>
            <td>
              {
                orders.filter((order) => order.customerId === customer.id)
                  .length
              }
            </td>
            <td></td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
function C9(products) {
  const [filterProduct, setFilterProducts] = useState(products);

  const filterByPrice = (price) => {
    const filtered = products.filter((product) => product.price === price);
    setFilterProducts(filtered);
  };

  return (
    <div className="mt-5">
      <Dropdown>
        <Dropdown.Toggle variant="primary" id="dropdown-basic">
          Filer By Price
        </Dropdown.Toggle>
        <Dropdown.Menu>
          {products.map((product) => (
            <Dropdown.Item onClick={() => filterByPrice(product.price)}>
              {product.price}
            </Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>

      <div className="product-list" style={{ display: "flex", gap: "20px" }}>
        {filterProduct.map((product) => (
          <div key={product.id} className="product-item">
            <h3>{product.name}</h3>
            <p>${product.price}</p>
            <img
              src={product.imageUrl}
              alt={product.name}
              style={{ width: "100px", height: "100px" }}
            />
          </div>
        ))}
      </div>
    </div>
  );
}
function App() {
  return (
    <div>
      <AddNewCustomer />
      {c3(customers, orders)}
      {C4(orders)}
      {C5(products)}
      {c6(products)}
      <h1>Question 10</h1>
      {c10(customers, orders, products)}
      <h1>Search customer by name or city</h1>
      {C8(customers)}
      {c7(customers, orders)}
      {C9(products)}
    </div>
  );
}

export default App;
