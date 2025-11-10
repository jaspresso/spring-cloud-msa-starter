// src/api/apiClient.js
import axios from 'axios';

// ✅ API Gateway를 통해 MSA 백엔드와 통신
const apiClient = axios.create({
  baseURL: 'http://localhost:8000', // Gateway 주소
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 시 JWT 토큰 자동 추가
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default apiClient;
