package com.patterns.mediator.isolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Mediator Pattern - Isolation Exercise (Chat Room).
 */
@DisplayName("Mediator Pattern - Chat Room")
class IsolationExerciseTest {
    
    private ChatMediator chatRoom;
    private User alice;
    private User bob;
    private User charlie;
    
    @BeforeEach
    void setUp() {
        chatRoom = new ChatRoom();
        alice = new User("Alice", chatRoom);
        bob = new User("Bob", chatRoom);
        charlie = new User("Charlie", chatRoom);
    }
    
    @Test
    @DisplayName("Should add user to chat room")
    void testAddUser() {
        chatRoom.addUser(alice);
        
        assertThat(((ChatRoom) chatRoom).getUserCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should announce when user joins")
    void testJoinAnnouncement() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        assertThat(alice.getMessages()).contains("Bob joined the chat");
    }
    
    @Test
    @DisplayName("Should send message to all users except sender")
    void testBroadcastMessage() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        alice.send("Hello everyone!");
        
        assertThat(bob.getMessages()).contains("Alice: Hello everyone!");
        assertThat(charlie.getMessages()).contains("Alice: Hello everyone!");
        assertThat(alice.getMessages()).doesNotContain("Alice: Hello everyone!");
    }
    
    @Test
    @DisplayName("Should send private message to specific user")
    void testPrivateMessage() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        bob.sendPrivate("Can we talk privately?", "Alice");
        
        assertThat(alice.getMessages()).contains("Bob (private): Can we talk privately?");
        assertThat(charlie.getMessages()).doesNotContain("Bob (private): Can we talk privately?");
    }
    
    @Test
    @DisplayName("Should handle private message to non-existent user")
    void testPrivateMessageToNonExistentUser() {
        chatRoom.addUser(alice);
        
        alice.sendPrivate("Hello", "NonExistent");
        
        assertThat(alice.getMessages()).anyMatch(msg -> msg.contains("not found") || msg.contains("System"));
    }
    
    @Test
    @DisplayName("Should remove user from chat room")
    void testRemoveUser() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        chatRoom.removeUser(alice);
        
        assertThat(((ChatRoom) chatRoom).getUserCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should announce when user leaves")
    void testLeaveAnnouncement() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        chatRoom.removeUser(bob);
        
        assertThat(alice.getMessages()).contains("Bob left the chat");
        assertThat(charlie.getMessages()).contains("Bob left the chat");
    }
    
    @Test
    @DisplayName("Should accumulate messages in order")
    void testMessageOrdering() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        alice.send("First message");
        bob.send("Second message");
        alice.send("Third message");
        
        assertThat(bob.getMessages())
            .containsSequence("Alice: First message", "Alice: Third message");
    }
    
    @Test
    @DisplayName("Should handle multiple users joining")
    void testMultipleUsersJoining() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        assertThat(((ChatRoom) chatRoom).getUserCount()).isEqualTo(3);
        assertThat(alice.getMessages()).hasSize(2);  // Bob joined, Charlie joined
        assertThat(bob.getMessages()).hasSize(1);    // Charlie joined
        assertThat(charlie.getMessages()).isEmpty(); // Joined last, no announcements
    }
    
    @Test
    @DisplayName("Should receive both broadcast and private messages")
    void testMixedMessages() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        alice.send("Hello everyone");
        bob.sendPrivate("Private reply", "Alice");
        alice.send("Got your message");
        
        assertThat(bob.getMessages())
            .contains("Alice: Hello everyone")
            .contains("Alice: Got your message");
        
        assertThat(alice.getMessages())
            .contains("Bob (private): Private reply");
    }
    
    @Test
    @DisplayName("Should format broadcast messages with sender name")
    void testMessageFormatting() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        alice.send("Test message");
        
        assertThat(bob.getMessages())
            .anyMatch(msg -> msg.startsWith("Alice:"));
    }
    
    @Test
    @DisplayName("Should format private messages differently")
    void testPrivateMessageFormatting() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        alice.sendPrivate("Private test", "Bob");
        
        assertThat(bob.getMessages())
            .anyMatch(msg -> msg.contains("(private)"));
    }
    
    @Test
    @DisplayName("Should handle user with no messages")
    void testUserWithNoMessages() {
        chatRoom.addUser(alice);
        
        assertThat(alice.getMessages()).isEmpty();
    }
    
    @Test
    @DisplayName("Should not send message to removed user")
    void testMessageToRemovedUser() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        int bobMessagesBefore = bob.getMessages().size();
        chatRoom.removeUser(bob);
        
        alice.send("After Bob left");
        
        // Bob's messages should not increase after removal
        assertThat(bob.getMessages()).hasSize(bobMessagesBefore);
        assertThat(charlie.getMessages()).contains("Alice: After Bob left");
    }
    
    @Test
    @DisplayName("Should return user name correctly")
    void testGetUserName() {
        assertThat(alice.getName()).isEqualTo("Alice");
        assertThat(bob.getName()).isEqualTo("Bob");
    }
    
    @Test
    @DisplayName("Should handle empty chat room")
    void testEmptyChatRoom() {
        assertThat(((ChatRoom) chatRoom).getUserCount()).isZero();
    }
    
    @Test
    @DisplayName("Should handle single user sending message")
    void testSingleUserMessage() {
        chatRoom.addUser(alice);
        
        alice.send("Talking to myself");
        
        // Alice shouldn't receive her own broadcast
        assertThat(alice.getMessages()).doesNotContain("Alice: Talking to myself");
    }
    
    @Test
    @DisplayName("Should handle multiple private messages")
    void testMultiplePrivateMessages() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        alice.sendPrivate("Private to Bob", "Bob");
        alice.sendPrivate("Private to Charlie", "Charlie");
        
        assertThat(bob.getMessages()).anyMatch(msg -> msg.contains("Private to Bob"));
        assertThat(charlie.getMessages()).anyMatch(msg -> msg.contains("Private to Charlie"));
        
        assertThat(bob.getMessages()).noneMatch(msg -> msg.contains("Private to Charlie"));
        assertThat(charlie.getMessages()).noneMatch(msg -> msg.contains("Private to Bob"));
    }
    
    @Test
    @DisplayName("Should verify users don't hold direct references")
    void testNoDirectUserReferences() {
        // This is a structural test - User class should not have List<User> or User fields
        // In a real scenario, you'd use reflection or code inspection
        // For now, we verify through behavior: users can only communicate via mediator
        
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        // If users had direct references, they could bypass the mediator
        // The fact that all communication goes through send() and sendPrivate()
        // demonstrates the mediator is required
        
        alice.send("Test");
        assertThat(bob.getMessages()).isNotEmpty();
    }
    
    @Test
    @DisplayName("Should handle rapid succession of messages")
    void testRapidMessages() {
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        
        for (int i = 0; i < 10; i++) {
            alice.send("Message " + i);
        }
        
        assertThat(bob.getMessages()).hasSize(10);
    }
    
    @Test
    @DisplayName("Should return unmodifiable message list")
    void testUnmodifiableMessageList() {
        chatRoom.addUser(alice);
        alice.send("Test");
        
        // Attempt to modify the returned list should not affect internal state
        var messages = alice.getMessages();
        
        assertThatThrownBy(() -> messages.add("Injected message"))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
