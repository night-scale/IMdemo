<template>
  <div id="home-page">
    <div class="container">
      <!-- Left Sidebar -->
      <div class="sidebar">
        <div class="button-search-container">
          <button id="profile-button" class="profile-button" @click="toggleProfileDropdown"></button>
          <input type="text" class="home-search-bar" placeholder="Search">
        </div>

        <!-- Profile Dropdown Menu -->
        <div v-if="profileDropdownVisible" class="profile-dropdown">
          <div class="profile-header">
            <img src="profile-avatar.jpg" alt="Profile" class="profile-avatar">
            <span class="profile-name">chaoRan</span>
            <small><a href="#">Set Emoji Status</a></small>
          </div>
          <ul class="profile-menu">
            <li><a href="#">New Group</a></li>
            <li><a href="#">New Channel</a></li>
            <li><a href="#">Contacts</a></li>
            <li><a href="#">Calls</a></li>
            <li><a href="#">Saved Messages</a></li>
            <li><a href="#">Settings</a></li>
            <li><a href="#">Night Mode <input type="checkbox"></a></li>
          </ul>
        </div>

        <div class="group-list">
          <!-- Group Items - Example -->
          <div class="group-item" v-for="(group, index) in groups" :key="index">
            <img src="profile-avatar.jpg" alt="Group" class="group-avatar">
            <div class="group-details">
              <span class="group-name">{{ group.name }}</span>
              <span class="last-message">{{ group.lastMessage }}</span>
              <span class="message-time">{{ group.time }}</span>
            </div>
            <span class="unread-count">{{ group.unread }}</span>
          </div>
        </div>
      </div>

      <div class="chat-area">
        <!-- Chat Header -->
        <div class="chat-header">
          <span>Select a chat to start messaging</span>
        </div>
        
        <!-- Chat Content Area -->
        <div class="chat-content">
          <!-- Chat messages will appear here -->
        </div>
        
        <!-- Message Input Area -->
        <div class="message-input">
          <input type="text" placeholder="Write a message..." class="message-box">
          <button class="send-button">Send</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      profileDropdownVisible: false,
      groups: [
        { name: 'Group Name', lastMessage: 'Last message preview...', time: '12:34', unread: 5 },
        // 其他组的数据
      ]
    }
  },
  methods: {
    toggleProfileDropdown() {
      this.profileDropdownVisible = !this.profileDropdownVisible;
    }
  }
}
</script>

<style scoped>
  /* 主页面样式 */
  #home-page .container {
    display: flex;
    height: 100vh;
  }

  .sidebar {
    width: 300px;
    background-color: #2c3e50;
    color: white;
    padding: 20px;
    display: flex;
    flex-direction: column;
  }

  .button-search-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .profile-button {
    width: 40px;
    height: 40px;
    background: url('profile-avatar.jpg') no-repeat center center;
    background-size: cover;
    border: none;
    border-radius: 50%;
    cursor: pointer;
  }

  .home-search-bar {
    flex-grow: 1;
    margin-left: 10px;
    padding: 10px;
    border-radius: 4px;
    border: none;
    outline: none;
  }

  .profile-dropdown {
    background-color: #34495e;
    padding: 20px;
    margin-top: 10px;
    border-radius: 8px;
  }

  .profile-avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
  }

  .profile-name {
    font-size: 18px;
    font-weight: bold;
    margin-left: 10px;
  }

  .profile-menu {
    list-style: none;
    padding: 0;
    margin-top: 10px;
  }

  .profile-menu li {
    margin: 10px 0;
  }

  .profile-menu li a {
    color: white;
    text-decoration: none;
  }

  .profile-menu li a:hover {
    text-decoration: underline;
  }

  /* 群组列表样式 */
  .group-list {
    margin-top: 20px;
  }

  .group-item {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }

  .group-avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
  }

  .group-details {
    flex-grow: 1;
  }

  .group-name {
    font-weight: bold;
    color: white;
  }

  .last-message {
    font-size: 12px;
    color: #bdc3c7;
  }

  .message-time {
    font-size: 12px;
    color: #bdc3c7;
    margin-left: 10px;
  }

  .unread-count {
    background-color: #e74c3c;
    color: white;
    padding: 5px 10px;
    border-radius: 50%;
    font-size: 12px;
  }

  /* 聊天区域样式 */
  .chat-area {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    background-color: #ecf0f1;
  }

  .chat-header {
    padding: 20px;
    background-color: #3498db;
    color: white;
    font-size: 18px;
    text-align: center;
  }

  .chat-content {
    flex-grow: 1;
    padding: 20px;
    overflow-y: auto;
    background-color: white;
  }

  .message-input {
    padding: 10px;
    background-color: #f1f1f1;
    display: flex;
    align-items: center;
  }

  .message-box {
    flex-grow: 1;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    outline: none;
  }

  .send-button {
    padding: 10px 20px;
    background-color: #3498db;
    color: white;
    border: none;
    border-radius: 4px;
    margin-left: 10px;
    cursor: pointer;
  }

  .send-button:hover {
    background-color: #2980b9;
  }
</style>
