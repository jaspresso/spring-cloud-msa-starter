import React, { useState } from 'react';
import apiClient from '../api/apiClient';

function SignupPage() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      // ✅ API Gateway를 통해 요청 전송
      const response = await apiClient.post('/users/signup', {
        name,
        email,
        password,
      });

      console.log('회원가입 성공:', response.data);
      setMessage('회원가입 성공!');
    } catch (error) {
      console.error('회원가입 실패:', error);
      setMessage('회원가입 실패. 다시 시도하세요.');
    }
  };

  return (
    <div>
      <h2>회원가입</h2>
      <form onSubmit={handleSignup}>
        <input type="text" placeholder="이름" value={name} onChange={(e) => setName(e.target.value)} /><br />
        <input type="email" placeholder="이메일" value={email} onChange={(e) => setEmail(e.target.value)} /><br />
        <input type="password" placeholder="비밀번호" value={password} onChange={(e) => setPassword(e.target.value)} /><br />
        <button type="submit">회원가입</button>
      </form>
      <p>{message}</p>
    </div>
  );
}

export default SignupPage;
