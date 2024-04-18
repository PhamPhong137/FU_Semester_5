import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, FormControl, InputGroup } from "react-bootstrap";
import axios from "axios";

function SearchProduct() {
  const [products, setProducts] = useState([]);
  const [searchQuery, setSearchQuery] = useState(""); 

  useEffect(() => {
    fetchProducts();
  }, [products]);

  const fetchProducts = async () => {
    const response = await axios.get("http://localhost:3000/products");
    setProducts(response.data);
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value.toLowerCase());
  };

  const filteredProducts = products.filter((product) =>
    product.name.toLowerCase().includes(searchQuery)
  );

  return (
    <>
    <h2>Search Product</h2>
      <InputGroup className="mb-3">
        <FormControl
          placeholder="Search product..."
          aria-label="Search product"
          onChange={handleSearchChange}
        />
      </InputGroup>

      <Table striped bordered hover>   
        <tbody>
          {filteredProducts.map((product) => {          
            return (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>{product.categoryId}</td>
                <td>{product.price}</td>
                <td>{product.description}</td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </>
  );
}

export default SearchProduct;
