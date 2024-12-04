<template>
    <div id="login-page">
      <div class="login-container">
        <h2>Login</h2>
        <input type="text" placeholder="Username" v-model="username">
        <input type="password" placeholder="Password" v-model="password">
        <button @click="login">Login</button>
        <p v-if="errorMessage" style="color:red;">{{ errorMessage }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        username: '',
        password: '',
        errorMessage: ''
      }
    },
    methods: {
      login() {
        // 示例登录逻辑
        if (this.username === 'admin' && this.password === 'password') {
          this.$emit('login-success');
        } else {
          this.errorMessage = 'Invalid username or password';
        }
      }
    }
  }
  </script>
  
  <style scoped>
/* 登录页样式 */
#login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-container {
  background-color: #fff;
  padding: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 300px;
}

.login-container h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.login-container input {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.login-container button {
  width: 100%;
  padding: 10px;
  background-color: #28a745;
  border: none;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.login-container button:hover {
  background-color: #218838;
}

  </style>
  