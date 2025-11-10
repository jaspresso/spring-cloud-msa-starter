// src/pages/LoginPage.js
import React, { useState } from 'react';
import apiClient from '../api/apiClient';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // ✅ user-service의 로그인 엔드포인트 호출
      const res = await apiClient.post('/user-service/login', form);
      localStorage.setItem('token', res.data.token); // JWT 저장
      navigate('/users');
    } catch (err) {
      console.error('로그인 실패:', err);
      setError('❌ 로그인 실패. 이메일 또는 비밀번호를 확인하세요.');
    }
  };

  return (
    <div style={{ padding: 30 }}>
      <h2>로그인</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          name="email"
          placeholder="이메일"
          value={form.email}
          onChange={handleChange}
          required
        /><br /><br />
        <input
          type="password"
          name="password"
          placeholder="비밀번호"
          value={form.password}
          onChange={handleChange}
          required
        /><br /><br />
        <button type="submit">로그인</button>
      </form>
      {error && <p style={{ color: 'red', marginTop: 10 }}>{error}</p>}
    </div>
  );
}

export default LoginPage;
