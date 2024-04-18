import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";

import axios from "axios";

function ProductList() {
  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState("");

  const [selectedProduct, setSelectedProduct] = useState(null);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);

  const handleShow = (product) => {
    setId(product.id);
    setName(product.name);
    setCategoryId(product.categoryId);
    setPrice(product.price);
    setDescription(product.description);

    setSelectedProduct(product);
    setShow(true);
  };

  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, []);

  const fetchProducts = async () => {
    const response = await axios.get("http://localhost:3000/products");
    setProducts(response.data);
  };

  const fetchCategories = async () => {
    const response = await axios.get("http://localhost:3000/categories");
    setCategories(response.data);
  };
  const deleteProduct = async (id) => {
    await axios.delete(`http://localhost:3000/products/${id}`);
    fetchProducts(); 
  };

  const updateProduct = async () => {
    console.log(id);
    await axios.put(`http://localhost:3000/products/${id}`, {
      id,
      name,
      categoryId,
      price,
      description,
    });
    fetchProducts(); 
    handleClose(); 
  };

  return (
    <>
      <h1 style={{ textAlign: "center" }}>Infomation of product</h1>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>CategoryId</th>
            <th>CategoryName</th>
            <th>Price</th>
            <th>Description</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => {
            const categoryName = categories.find(
              (category) =>
                category.id.toString() === product.categoryId.toString()
            )?.name;
            return (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>{product.categoryId}</td>
                <td>{categoryName}</td>
                <td>{product.price}</td>
                <td>{product.description}</td>
                <td>
                  <Button variant="primary" onClick={() => handleShow(product)}>
                    Update
                  </Button>

                  <Modal show={show} onHide={handleClose}>
                    <Modal.Header closeButton>
                      <Modal.Title>UpdateProduct</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                      <Form.Group className="mb-3">
                        <Form.Label>Id</Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="id"
                          value={selectedProduct?.id}
                          onChange={(e) => setId(e.target.value)}
                        />
                      </Form.Group>
                      <Form.Group className="mb-3">
                        <Form.Label>Name</Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="name"
                          value={name}
                          onChange={(e) => setName(e.target.value)}
                        />
                      </Form.Group>
                      <Form.Group className="mb-3">
                        <Form.Label>CategoryId</Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="CategoryId"
                          value={categoryId}
                          onChange={(e) => setCategoryId(e.target.value)}
                        />
                      </Form.Group>
                      <Form.Group className="mb-3">
                        <Form.Label>Price</Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="Price"
                          value={price}
                          onChange={(e) => setPrice(e.target.value)}
                        />
                      </Form.Group>
                      <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                          type="text"
                          placeholder="Description"
                          value={description}
                          onChange={(e) => setDescription(e.target.value)}
                        />
                      </Form.Group>
                    </Modal.Body>
                    <Modal.Footer>
                      <Button variant="secondary" onClick={handleClose}>
                        Close
                      </Button>
                      <Button variant="primary" onClick={updateProduct}>
                        Save Changes
                      </Button>
                    </Modal.Footer>
                  </Modal>

                  <Button
                    variant="danger"
                    onClick={() => deleteProduct(product.id)}
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
  );
}

export default ProductList;
