import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Container, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const Slide19_20 = () => {
  const [posts, setPosts] = useState([]);
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  
  useEffect(() => {
    fetchPosts();
  }, [posts]);

  const fetchPosts = async () => {
    const response = await axios.get('http://localhost:3000/posts');
    setPosts(response.data);
  };

  const createPost = async () => {
    const post = { title, author };
    await axios.post('http://localhost:3000/posts', post);

  };

  const updatePost = async (id) => {
    const post = { title: 'Updated Title', author: 'Updated Author' };
    await axios.put(`http://localhost:3000/posts/${id}`, post);

  };

  const deletePost = async (id) => {
    await axios.delete(`http://localhost:3000/posts/${id}`);

  };

  return (
    <Container>
      <h1>Posts</h1>
      {posts.map(post => (
        <div key={post.id}>
          <h4>{post.title}</h4>
          <p>{post.author}</p>
          <Button onClick={() => updatePost(post.id)}>Update</Button>
          <Button variant="danger" onClick={() => deletePost(post.id)}>Delete</Button>
        </div>
      ))}
      <Form>
        <Form.Group className="mb-3">
          <Form.Label>Title</Form.Label>
          <Form.Control type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Author</Form.Label>
          <Form.Control type="text" value={author} onChange={(e) => setAuthor(e.target.value)} />
        </Form.Group>
        <Button onClick={createPost}>Add Post</Button>
      </Form>
    </Container>
  );
};

export default Slide19_20;
