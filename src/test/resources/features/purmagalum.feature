Feature:Purmagalum services


  @core @all
  Scenario Outline:Verify text formats
    Given User enters text <text> and contentType <contentType>
    Then should see that result matches the given content Type
    Examples:
      | text                 | contentType |
      | this is a json text  | json        |
      | this is a xml text   | xml         |
      | this is a plain text | plain       |


  @core @all
  Scenario Outline:Verify profanity checking service
    Given User enters text <text> and checks <service>
    Then be able to verify that either contains or not profanity word and yield to <result>
    Examples:
      | text                                         | service           | result |
      | this sentence doesn't contain profanity word | containsprofanity | false  |
      | this sentence contains this ``shit`` word    | containsprofanity | true   |


  @core @advanced @all
  Scenario Outline:Verify advanced methods
    Given User enters text <originalText> with the format <contentType>
    When add the advance service <service1> with <select> and <service2> with <replace>
    Then be able to verify that result <result> will be as expected
    Examples:
      | originalText     | contentType | service1 | select    | service2  | replace | result           |
      | this is original | xml         | add      | original  | null      |         | this is ******** |
      | this is original | json        | add      | original  | null      |         | this is ******** |
      | this is original | plain       | add      | original  | null      |         | this is ******** |
      | this is original | xml         | add      | original  | fill_text | [not]   | this is [not]    |
      | this is original | json        | add      | original  | fill_text | [not]   | this is [not]    |
      | this is original | plain       | add      | original  | fill_text | [not]   | this is [not]    |
      | this is original | xml         | add      | o,r,g,n,l | fill_char | _       | this is __i_i_a_ |
      | this is original | json        | add      | o,r,g,n,l | fill_char | _       | this is __i_i_a_ |
      | this is original | plain       | add      | o,r,g,n,l | fill_char | _       | this is __i_i_a_ |


  @error @all
  Scenario Outline:Verify Errors
    Given User enters text <originalText> with the format <contentType>
    When add the advance service <service1> with <select> and <service2> with <replace>
    Then be able to verify that error <error> will be as expected
    Examples:
      | originalText     | contentType | service1 | select   | service2  | replace                                  | error                                                 |
      | this is original | xml         | add      | original | fill_text | originaloriginaloriginaloriginaloriginal | User Replacement Text Exceeds Limit of 20 Characters. |
      | this is original | json        | add      | original | fill_text | originaloriginaloriginaloriginaloriginal | User Replacement Text Exceeds Limit of 20 Characters. |
      | this is original | plain       | add      | original | fill_text | originaloriginaloriginaloriginaloriginal | User Replacement Text Exceeds Limit of 20 Characters. |

