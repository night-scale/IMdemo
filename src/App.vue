<template>
  <div id="app">
    <Login v-if="!isAuthenticated" @login-success="handleLoginSuccess" />
    <Home v-else />
  </div>
</template>

<script>
import Login from './views/Login.vue';
import Home from './views/Home.vue';

export default {
  components: {
    Login,
    Home
  },
  data() {
    return {
      isAuthenticated: false
    }
  },
  methods: {
    handleLoginSuccess() {
      this.isAuthenticated = true;
    }
  }
}
</script>

<style>
/* 全局样式可以放在这里 */
/* 全局样式 */
body {
  font-family: Arial, sans-serif;
  background-color: #f0f0f0;
  margin: 0;
  padding: 0;
}

#app {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
