// Import các thư viện cần thiết
import React from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  useParams
} from 'react-router-dom';

// Component UsersContainer hiển thị danh sách người dùng
function UsersContainer() {
  const users = [
    { id: 1, name: 'Người dùng 1' },
    { id: 2, name: 'Người dùng 2' },
  ];

  return (
    <div>
      <h2>Danh sách người dùng</h2>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            <Link to={`/users/${user.id}`}>{user.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

// Component UserContainer hiển thị thông tin chi tiết của một người dùng
function UserContainer() {
  let { id } = useParams();
  return (
    <div>
      <h2>Thông tin người dùng {id}</h2>
    </div>
  );
}

// Component App bao gồm cấu trúc định tuyến
function Slide14_15() {
  return (
    <Router>
      <nav>
        <Link to="/">Trang chủ</Link>
      </nav>
      <hr />
      <Routes>
        <Route path="users" element={<UsersContainer />} />
        <Route path="users/:id" element={<UserContainer />} />
      </Routes>
    </Router>
  );
}

export default Slide14_15;
