import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, FormControl, InputGroup } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { Form } from "react-bootstrap";
import { Modal } from "react-bootstrap";

import axios from "axios";

function SearchOrders() {
  const [orders, setOrders] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [products, setProducts] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);

  const [selectedOrder, setSelectedOrder] = useState(null);
  const [quantity, setQuantity] = useState(1);

  const [selectedProductId, setSelectedProductId] = useState("");

  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  useEffect(() => {
    fetchCutomers();
    fetchProducts();
    fetchOrders();
  }, [orders]);

  const fetchProducts = async () => {
    const response = await axios.get("http://localhost:3000/products");
    setProducts(response.data);
  };
  const fetchCutomers = async () => {
    const response = await axios.get("http://localhost:3000/customers");
    setCustomers(response.data);
  };
  const fetchOrders = async () => {
    const response = await axios.get("http://localhost:3000/orders");
    setOrders(response.data);
  };

  const deleteOrder = async (id) => {
    await axios.delete(`http://localhost:3000/orders/${id}`);
  };

  const updateOrder = async () => {
    const id = selectedOrder.id;
    await axios.put(`http://localhost:3000/orders/${id}`, {
      customerId: selectedOrder.customerId,
      productId: selectedProductId,
      quantity: quantity,
    });

    handleClose();
  };

  const handleShow = (order) => {
    setSelectedOrder(order);
    setSelectedProductId(order.productId);
    setQuantity(order.quantity);
    setShow(true);
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value.toLowerCase());
  };

  const getCustomerName = (customerId) => {
    const customer = customers.find(
      (customer) => parseInt(customer.id) === parseInt(customerId)
    );
    return customer ? customer.name : "Unknown Customer";
  };

  const getProductName = (productId) => {
    const product = products.find((product) => product.id === productId);
    return product ? product.name : "Unknown Product";
  };

  const filteredOrders = orders.filter(
    (order) =>
      getProductName(order.productId).toLowerCase().includes(searchQuery) ||
      getCustomerName(order.customerId).toLowerCase().includes(searchQuery)
  );
  console.log(filteredOrders);
  return (
    <>
      {isLoggedIn && (
        <>
          <h2>Search Orders</h2>
          <InputGroup className="mb-3">
            <FormControl
              placeholder="Search orders by product or customer..."
              aria-label="Search orders"
              onChange={handleSearchChange}
            />
            <DropdownButton
              variant="outline-secondary"
              title="Product"
              id="input-group-dropdown-1"
            >
              {products.map((product) => {
                return (
                  <Dropdown.Item key={product.id}>{product.name}</Dropdown.Item>
                );
              })}
            </DropdownButton>
            <Button variant="primary">Search</Button>
          </InputGroup>

          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Order ID</th>
                <th>Customer Name</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filteredOrders.map((order) => {
                return (
                  <tr key={order.id}>
                    <td>{order.id}</td>
                    <td>{getCustomerName(order.customerId)}</td>
                    <td>{getProductName(order.productId)}</td>
                    <td>{order.quantity}</td>
                    <td>
                      <Button
                        variant="primary"
                        onClick={() => handleShow(order)}
                      >
                        Update
                      </Button>

                      <Modal show={show} onHide={handleClose}>
                        <Modal.Header closeButton>
                          <Modal.Title>UpdateProduct</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                          <Form.Group className="mb-3">
                            <Form.Label> Product Name</Form.Label>
                            <Form.Select
                              value={selectedProductId}
                              onChange={(e) =>
                                setSelectedProductId(e.target.value)
                              }
                            >
                              {products.map((product) => (
                                <option key={product.id} value={product.id}>
                                  {product.name}
                                </option>
                              ))}
                            </Form.Select>
                          </Form.Group>
                          <Form.Group className="mb-3">
                            <Form.Label>Quantity</Form.Label>
                            <Form.Control
                              type="text"
                              placeholder="quantity"
                              value={quantity}
                              onChange={(e) => setQuantity(e.target.value)}
                            />
                          </Form.Group>
                        </Modal.Body>

                        <Modal.Footer>
                          <Button variant="secondary" onClick={handleClose}>
                            Close
                          </Button>
                          <Button variant="primary" onClick={updateOrder}>
                            Save Changes
                          </Button>
                        </Modal.Footer>
                      </Modal>

                      <Button
                        variant="danger"
                        onClick={() => deleteOrder(order.id)}
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
        </>
      )}
    </>
  );
}

export default SearchOrders;
