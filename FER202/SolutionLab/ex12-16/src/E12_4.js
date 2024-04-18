import React, { useState } from 'react';

const E12_4 = () => {
  const [tasks, setTasks] = useState([]); // Trạng thái cho danh sách công việc
  const [newTask, setNewTask] = useState(''); // Trạng thái cho công việc mới

  // Hàm xử lý thêm công việc mới
  const handleAddTask = () => {
    if (newTask) { // Kiểm tra xem công việc mới có rỗng không
      setTasks([...tasks, newTask]); // Thêm công việc mới vào danh sách
      setNewTask(''); // Đặt lại trạng thái công việc mới
    }
  };

  // Hàm xử lý xoá công việc
  const handleDeleteTask = (index) => {
    const updatedTasks = tasks.filter((task, i) => i !== index); // Xoá công việc dựa trên index
    setTasks(updatedTasks); // Cập nhật danh sách công việc
  };

  return (
    <div>
      <div>
        <input
          type="text"
          value={newTask}
          onChange={(e) => setNewTask(e.target.value)}
          placeholder="Please input a Task"
        />
        <button onClick={handleAddTask}>Add Todo</button>
      </div>
      <div>
        <h3>Todo List</h3>
        <ul>
          {tasks.map((task, index) => (
            <li key={index}>
              {task} <button onClick={() => handleDeleteTask(index)}>Delete</button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default E12_4;
