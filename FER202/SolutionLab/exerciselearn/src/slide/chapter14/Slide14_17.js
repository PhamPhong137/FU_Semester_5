import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useParams } from 'react-router-dom';

function UsersContainer() {
    let { desc } = useParams();
  
    // Giả sử chúng ta có một danh sách người dùng làm mẫu
    const usersSample = [
      { id: 1, name: 'Nguyễn Văn A', age: 20 },
      { id: 2, name: 'Trần Thị B', age: 28 },
      { id: 3, name: 'Lê Văn C', age: 25 }
    ];
  
    // Hàm để sắp xếp người dùng giảm dần theo tuổi
    const sortUsersDescending = (users) => {
      return users.sort((a, b) => b.age - a.age);
    };
  
    let usersToDisplay = usersSample;
  
    if (desc === 'desc') {
      usersToDisplay = sortUsersDescending([...usersSample]); // Sử dụng spread để tạo bản sao và không thay đổi mảng gốc
    }
  
    return (
      <div>
        <h1>Danh sách người dùng</h1>
        {desc === 'desc' ? <h2>Sắp xếp giảm dần</h2> : <h2>Không sắp xếp</h2>}
        <ul>
          {usersToDisplay.map(user => (
            <li key={user.id}>
              {user.name} - Tuổi: {user.age}
            </li>
          ))}
        </ul>
      </div>
    );
  }
function Slide14_17() {
    return (
        <Router>
            <Routes>
                <Route path="/users">
                    <Route path="" element={<UsersContainer />} />
                    <Route path=":desc" element={<UsersContainer />} />
                </Route>
            </Routes>
        </Router>
    );
}

export default Slide14_17;
