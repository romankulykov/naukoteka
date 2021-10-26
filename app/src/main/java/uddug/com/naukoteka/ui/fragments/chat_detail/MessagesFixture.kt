package uddug.com.naukoteka.ui.fragments.chat_detail

import java.util.*

class MessagesFixture : FixturesData() {
    private fun MessagesFixture() {
        throw AssertionError()
    }

    fun getImageMessage(): Message {
        val message = Message(getRandomId(), getUser(), null)
        message.setImage(Message.Image(getRandomImage()))
        return message
    }

    fun getVoiceMessage(): Message {
        val message = Message(getRandomId(), getUser(), null)
        message.setVoice(Message.Voice("http://example.com", rnd.nextInt(200) + 30))
        return message
    }

    fun getTextMessage(): Message {
        return getTextMessage(getRandomMessage())
    }

    fun getTextMessage(text: String?): Message {
        return Message(getRandomId(), getUser(), text)
    }

    fun getMessages(startDate: Date?): ArrayList<Message> {
        val messages = ArrayList<Message>()
        for (i in 0..9) {
            val countPerDay = rnd.nextInt(5) + 1
            for (j in 0 until countPerDay) {
                var message: Message
                message = if (i % 2 == 0 && j % 3 == 0) {
                    getImageMessage()
                } else {
                    getTextMessage()
                }
                val calendar = Calendar.getInstance()
                if (startDate != null) calendar.time = startDate
                calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1))
                message.createdAt = calendar.time
                messages.add(message)
            }
        }
        return messages
    }

    private fun getUser(): User {
        val even = rnd.nextBoolean()
        return User(
            if (even) "0" else "1",
            if (even) names[0] else names[1],
            if (even) avatars[0] else avatars[1],
            true
        )
    }
}