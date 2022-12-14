package dev.reid.daos;

import dev.reid.entity.Meeting;
import dev.reid.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingDAOPostgres implements MeetingDAO {

    @Override
    public Meeting createMeeting(Meeting meeting) {
        System.out.println("MeetingDAOPostgres createMeeting");
        System.out.println(meeting);
        try(Connection conn = ConnectionUtil.createConnection())
        {


            String sql = "insert into meeting values (default, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //preparedStatement.setInt(1, expense.getId());
            preparedStatement.setString(1, meeting.getLocation());
            preparedStatement.setInt(2, meeting.getTime());
            preparedStatement.setString(3, meeting.getSummary());

            //System.out.println(preparedStatement);

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();

            int generatedKey = rs.getInt("meeting_id");
            System.out.println("generatedKey: " + generatedKey);
            meeting.setId(generatedKey);

            //preparedStatement.execute();
            //System.out.println("EXpense" + expense);
            return meeting;



        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Meeting getMeetingByID(int id) {
        try(Connection connection = ConnectionUtil.createConnection())
        {


            String sql = "select * from meeting where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            if (!rs.next())
            {
                throw new RuntimeException();
            }
            Meeting meeting = new Meeting();
            meeting.setId(rs.getInt("meeting_id"));
            meeting.setTime(rs.getInt("meeting_date"));
            meeting.setLocation(rs.getString("meeting_location"));

            return meeting;


        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Meeting updateMeetingLocation(int id) {
        return null;
    }

    @Override
    public Meeting updateMeetingDate(int id) {
        return null;
    }

    @Override
    public List<Meeting> getListOfMeetings() {
        try (Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from meeting";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            List<Meeting> meetingList = new ArrayList();

            int tempCount = 0;



            while (rs.next())
            {
                //System.out.println(tempCount);
                Meeting meeting = new Meeting();
                meeting.setId(rs.getInt("meeting_id"));
                meeting.setTime(rs.getInt("meeting_date"));
                meetingList.add(meeting);
                meeting.setLocation(rs.getString("meeting_location"));
                meeting.setSummary(rs.getString("summary"));

                //tempCount++;

            }
            return meetingList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteMeetingByID(int id) {
        return false;
    }


}
