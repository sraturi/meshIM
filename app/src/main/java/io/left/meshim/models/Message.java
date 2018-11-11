package io.left.meshim.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.protobuf.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "Messages",
        indices = { @Index(value = {"RecipientId"}), @Index(value = {"SenderId"}) },
        foreignKeys =
                {
                        @ForeignKey(entity = User.class, parentColumns = "UserId",
                                childColumns = "SenderId"),
                        @ForeignKey(entity = User.class, parentColumns = "UserId",
                                childColumns = "RecipientId")
                })
public class Message implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MessageId")
    public int id;

    @ColumnInfo(name = "Contents")
    private String message;

    @ColumnInfo(name = "Timestamp")
    private Date date;

    @ColumnInfo(name = "SenderId")
    public int senderId;

    @Ignore
    private User sender;

    @ColumnInfo(name = "RecipientId")
    public int recipientId;

    @Ignore
    private User recipient;

    @ColumnInfo(name = "SentFromDevice")
    private boolean isMyMessage;

    @ColumnInfo(name = "IsRead")
    private boolean isRead;

    @ColumnInfo(name = "IsDelivered")
    private boolean isDelivered;

    //file stuff
    @ColumnInfo(name = "FilePath")
    private String filePath;
    @ColumnInfo(name = "FileExtension")
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public boolean isMyMessage() {
        return isMyMessage;
    }

    public void setIsMyMessage(boolean isMyMessage) {
        this.isMyMessage = isMyMessage;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public boolean isDelivered() {
        return isDelivered;
    }


    /**
     * Next simplest constructor, extrapolates data like database ids and the time and sends them
     * along to the biggest constructor,
     * {@link Message#Message(User, int, User, int, String, boolean, Date)}.
     *
     * @param sender user that sent the message
     * @param recipient target recipient of the message
     * @param message message contents
     * @param isMyMessage if this device's user sent the message
     * @param fileName file name of the file associated with the message
     * @param filePath path of the file
     */
    @Ignore
    public Message(User sender, User recipient, String message, boolean isMyMessage,
                   String filePath, String fileName) {
        this(sender, sender.id, recipient, recipient.id, message, isMyMessage, new Date());
        this.isRead = false;
        this.isDelivered = false;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    /**
     * Room constructor. NOTE: {@link Message#sender} and {@link Message#recipient} will be null
     * until they can be fetched from the database based on their UserId.
     *
     * @param senderId database id for sender
     * @param recipientId database id for recipient
     * @param message message contents
     * @param isMyMessage if this device's user sent the message
     * @param date date message was sent
     * @param fileName file name of the file associated with the message
     * @param filePath path of the file
     */
    public Message(int senderId, int recipientId, String message, boolean isMyMessage,
                   Date date,String filePath,String fileName) {
        this(null, senderId, null, recipientId, message, isMyMessage, date);
        this.isRead = false;
        this.isDelivered = false;
        this.fileName = fileName;
        this.filePath = filePath;

    }

    /**
     * Main constructor - sets all values from parameters.
     *
     * @param sender user that sent the message
     * @param senderId database id for sender
     * @param recipient target recipient of the message
     * @param recipientId database id for recipient
     * @param message message contents
     * @param isMyMessage if this device's user sent the message
     * @param date date message was sent
     */
    @Ignore
    public Message(User sender, int senderId, User recipient, int recipientId, String message,
                   boolean isMyMessage, Date date) {
        this.sender = sender;
        this.senderId = senderId;
        this.recipient = recipient;
        this.recipientId = recipientId;
        this.message = message;
        this.isMyMessage = isMyMessage;
        this.date = date;
        this.isRead = false;
        this.isDelivered = false;
        this.filePath = "";
        this.fileName = "";
    }

    /**
     * Converts local {@link Date} to a Protobuf {@link Timestamp} for use in data payloads.
     *
     * @return formatted timestamp
     */
    public Timestamp getDateAsTimestamp() {
        if (date != null) {
            long millis = date.getTime();
            return Timestamp.newBuilder()
                    .setSeconds(millis / 1000)
                    .setNanos((int) ((millis % 1000) * 1000000))
                    .build();
        } else {
            return null;
        }
    }

    /**
     * Instantiates a {@link Message }from a {@link Parcel}.
     * <p>
     *     Generated by Android Studio.
     * </p>
     * @param in Parcel to parse.
     */
    @Ignore
    protected Message(Parcel in) {
        id = in.readInt();
        message = in.readString();
        date = new Date(in.readLong());
        sender = in.readParcelable(User.class.getClassLoader());
        senderId = in.readInt();
        recipient = in.readParcelable(User.class.getClassLoader());
        recipientId = in.readInt();
        isMyMessage = in.readByte() != 0;
        this.isRead = false;
        this.isDelivered = in.readByte() != 0;
        this.filePath = in.readString();
        this.fileName = in.readString();
    }

    // Required by Parcelable, created by Android Studio.
    public static final Creator<Message> CREATOR = new Creator<Message>() {
        /**
         * {@inheritDoc}.
         */
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    /**
     * {@link Parcelable} default.
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes contents to a parcel.
     * <p>
     *     Generated by Android Studio.
     * </p>
     * @param dest parcel to write to
     * @param flags flags, I guess
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(message);
        dest.writeLong(date.getTime());
        dest.writeParcelable(sender, flags);
        dest.writeInt(senderId);
        dest.writeParcelable(recipient, flags);
        dest.writeInt(recipientId);
        dest.writeByte((byte) (isMyMessage ? 1 : 0));
        dest.writeInt((byte)(isDelivered ? 1 : 0));
        dest.writeString(filePath);
        dest.writeString(fileName);
    }

    /**
     * Function to format the date as desired.
     * @param date needing formatting
     * @return  the formatted date as a string
     */
    public static String formateDate(Date date) {
        DateFormat dateFormatter;
        Date today = new Date();
        if (date.getDate() == today.getDate()) {
            dateFormatter = new SimpleDateFormat("hh:mm a");
        } else if (date.getYear() == today.getYear()) {
            dateFormatter = new SimpleDateFormat("MMMM dd hh:mm a");
        } else {
            dateFormatter = new SimpleDateFormat("MMMM d yyyy hh:mm a");
        }
        String dateString = dateFormatter.format(date).toString();
        return dateString;
    }
}