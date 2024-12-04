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
  /* 添加相关的 CSS 样式 */
  .login-container {
    /* 这里可以添加样式 */
  }
  </style>
  