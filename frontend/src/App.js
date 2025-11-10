// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import SignupPage from './pages/SignupPage';
import LoginPage from './pages/LoginPage';
import UserListPage from './pages/UserListPage';

function App() {
  const isAuthenticated = !!localStorage.getItem('token');

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/users"
          element={isAuthenticated ? <UserListPage /> : <Navigate to="/login" />}
        />
      </Routes>
    </Router>
  );
}

export default App;
