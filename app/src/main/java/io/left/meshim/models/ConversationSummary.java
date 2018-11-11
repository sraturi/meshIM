package io.left.meshim.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import io.left.meshim.adapters.ConversationListAdapter;
import io.left.meshim.database.MeshIMDao;

import java.util.Date;

/**
 * A class that holds the information from {@link MeshIMDao#fetchConversationSummaries()} and is
 * used to populate {@link ConversationListAdapter}.
 */
public class ConversationSummary implements Parcelable {
    @ColumnInfo(name = "Username")
    public String username;

    @ColumnInfo(name = "Avatar")
    public int avatar;

    @ColumnInfo(name = "Contents")
    public String messageText;

    @ColumnInfo(name = "Timestamp")
    public Date messageTime;

    @ColumnInfo(name = "PeerId")
    public int peerId;

    @ColumnInfo(name = "IsRead")
    public boolean isRead;

    @ColumnInfo(name = "UnreadMessages")
    public int unreadMessages;

    @ColumnInfo(name = "IsDelivered")
    public boolean isDelivered;

    /**
     * General purpose setter-constructor used by Room.
     *
     * @param username username of user conversation is with
     * @param avatar avatar of user conversation is with
     * @param messageText contents of most recent message in conversation
     * @param messageTime time of most recent message in conversation
     * @param isRead a boolean to indicate if the last message was read
     * @param unreadMessages total number of unread message
     */
    public ConversationSummary(String username, int avatar, String messageText,
                               Date messageTime, boolean isRead, int unreadMessages) {
        this.username = username;
        this.avatar = avatar;
        this.messageText = messageText;
        this.messageTime = messageTime;
        this.isRead = isRead;
        this.unreadMessages = unreadMessages;
    }

    /**
     * Parcelable constructor.
     * @param in parcel to parse
     */
    @Ignore
    protected ConversationSummary(Parcel in) {
        username = in.readString();
        avatar = in.readInt();
        messageText = in.readString();
        messageTime = new Date(in.readLong());
        peerId = in.readInt();
        this.isRead = (in.readInt() == 1);
        this.unreadMessages = in.readInt();
        this.isDelivered = (in.readInt() == 1);
    }

    // Auto-generated Parcelable stuff.
    public static final Creator<ConversationSummary> CREATOR = new Creator<ConversationSummary>() {
        @Override
        public ConversationSummary createFromParcel(Parcel in) {
            return new ConversationSummary(in);
        }

        @Override
        public ConversationSummary[] newArray(int size) {
            return new ConversationSummary[size];
        }
    };

    /**
     * {@inheritDoc}.
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Writes the class to a {@link Parcel}.
     * @param dest parcel to write to
     * @param flags ignored flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(avatar);
        dest.writeString(messageText);
        dest.writeLong(messageTime.getTime());
        dest.writeInt(peerId);
        dest.writeInt(isRead ? 1 : 0);
        dest.writeInt(unreadMessages);
        dest.writeInt(isDelivered ? 1 : 0);

    }
}
