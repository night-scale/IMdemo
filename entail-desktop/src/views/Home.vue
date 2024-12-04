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
  /* 添加相关的 CSS 样式 */
  .sidebar {
    /* 侧边栏样式 */
  }
  
  .chat-area {
    /* 聊天区域样式 */
  }
  </style>
  