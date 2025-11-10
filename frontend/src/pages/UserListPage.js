// src/pages/UserListPage.js
import React, { useEffect, useState } from 'react';
import apiClient from '../api/apiClient';
import { useNavigate } from 'react-router-dom';

function UserListPage() {
  const navigate = useNavigate();
  const [users, setUsers] = useState([]);

  useEffect(() => {
    apiClient.get('/user-service/users')
      .then((res) => setUsers(res.data))
      .catch((err) => {
        console.error(err);
        if (err.response?.status === 401) {
          navigate('/login');
        }
      });
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div style={{ padding: 30 }}>
      <h2>사용자 목록</h2>
      <button onClick={handleLogout}>로그아웃</button>
      <table border="1" cellPadding="10" style={{ borderCollapse: 'collapse', marginTop: 20 }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>이름</th>
            <th>이메일</th>
          </tr>
        </thead>
        <tbody>
          {users.map((u) => (
            <tr key={u.userId}>
              <td>{u.userId}</td>
              <td>{u.name}</td>
              <td>{u.email}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UserListPage;
