import React, { useState, useEffect } from "react";
import axios from "axios";
import { Form, Button } from "react-bootstrap";
import { Row, Col } from "react-bootstrap";

function Addorder() {
  const [customers, setCustomers] = useState([]);
  const [products, setProducts] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState("");
  const [selectedProduct, setSelectedProduct] = useState("");
  const [quantity, setQuantity] = useState(1);

  const isLoggedIn = localStorage.getItem("isLoggedIn")==="true";

  useEffect(() => {
    axios.get("http://localhost:3000/customers").then((res) => {
      setCustomers(res.data);
      setSelectedCustomer(res.data[0]?.id);
    });

    axios.get("http://localhost:3000/products").then((res) => {
      setProducts(res.data);
      setSelectedProduct(res.data[0]?.id);
    });
  }, []);

  const handleSubmit = async (e) => {
    const newOrder = {
      customerId: selectedCustomer,
      productId: selectedProduct,
      quantity: quantity,
    };
    alert("Đơn hàng đã được thêm");

    await axios.post("http://localhost:3000/orders", newOrder);
  };

  return (
    <>
      {isLoggedIn && (
        <Form onSubmit={handleSubmit} style={{ margin: "10px 30px" }}>
          <Row>
            <Col>
              <Form.Group className="mb-3">
                <Form.Label>Customer</Form.Label>
                <Form.Select
                  value={selectedCustomer}
                  onChange={(e) => setSelectedCustomer(e.target.value)}
                >
                  {customers.map((customer) => (
                    <option key={customer.id} value={customer.id}>
                      {customer.name}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-3">
                <Form.Label>Product</Form.Label>
                <Form.Select
                  value={selectedProduct}
                  onChange={(e) => setSelectedProduct(e.target.value)}
                >
                  {products.map((product) => (
                    <option key={product.id} value={product.id}>
                      {product.name}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-3">
                <Form.Label>Quantity</Form.Label>
                <Form.Control
                  type="number"
                  value={quantity}
                  onChange={(e) => setQuantity(e.target.value)}
                  min="1"
                />
              </Form.Group>
            </Col>
          </Row>

          <Button variant="primary" type="submit">
            Add Order
          </Button>
        </Form>
      )}
    </>
  );
}

export default Addorder;
