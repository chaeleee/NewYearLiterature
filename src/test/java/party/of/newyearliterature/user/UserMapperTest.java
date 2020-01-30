package party.of.newyearliterature.user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * UserMapperTest
 */
public class UserMapperTest {

  @Test
  public void Given_UserDto_When_Map_Then_Return_User(){
    // Given
    UserDto userDto = UserDto.builder().email("test@test.com").name("식객").password("password123").build();

    // When
    User user = UserMapper.map(userDto);

    // Then
    assertEquals(userDto.getEmail(), user.getEmail());
    assertEquals(userDto.getPassword(), user.getPassword());
    assertEquals(userDto.getEmail(), user.getEmail());
  }
}