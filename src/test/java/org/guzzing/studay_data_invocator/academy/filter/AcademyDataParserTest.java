package org.guzzing.studay_data_invocator.academy.filter;

//@ExtendWith(MockitoExtension.class)
//public class AcademyDataParserTest {
//
//    @Mock
//    private DataFileReader dataFileReader;
//
//    @Mock
//    private Geocoder geocoder;
//
//    private AcademyDataParser dataParser;
//
//    @BeforeEach
//    void setUp() {
//        dataParser = new AcademyDataParser(dataFileReader, geocoder);
//    }
//
//    @Test
//    @DisplayName("원본 데이터 라인에서 필요한 컬럼만 추출하여 파싱한다.")
//    void parseData_Success() {
//        // Given
//        String csvData = "academyRegion, academyName, academyType, courseType, academyAddress, academyRepresentative, academyContact, courseLine, courseSubject, courseCurriculum, courseCapacity, courseDuration, courseTime, courseBasicFee, courseTestFee, courseMaterialFee, academyFoodFee, academyAccommodationFee, academyShuttleFee, academyClothesFee, courseTotalFee, academyTeacherCount\n"+
//                "성남,원뮤직(ONEMUSIC)스튜디오학원,학교교과교습학원,예능(대),경기도 성남시 분당구 느티로 65 (정자동/구미빌딩) 3층 403호,조미원,031-714-2472,예능(중),음악,피아노 초급,30,1개월0일,1125,130000,0,0,0,0,0,0,130000,0\n"+
//                "서울,뮤즈아카데미,학교교과교습학원,예능(대),서울시 강남구 역삼동 123-45 (뮤즈빌딩) 5층,홍길동,02-987-6543,예능(중),음악,기타 초급,20,2개월0일,1530,150000,0,0,0,0,0,0,150000,0";
//
//        String[] csvLines = csvData.split("\n");
//        when(dataFileReader.readFileData(anyString())).thenReturn(Arrays.asList(csvLines));
//        when(geocoder.addressToLocation(anyString())).thenReturn(Optional.of(Location.of(1.0, 2.0)));
//
//        // When
//        Map<Institute, List<Lesson>> result = dataParser.parseData("dummyFileName");
//
//        verify(dataFileReader).readFileData(anyString());
//        verify(geocoder, times(2)).addressToLocation(anyString());
//
//        // Then
//        assertEquals(2, result.size());
//        assertTrue(result.containsKey(Academy.of(AcademyInfo.of("원뮤직(ONEMUSIC)스튜디오학원", "031-714-2472", ""), Address.of("경기도 성남시 분당구 느티로 65 (정자동/구미빌딩) 3층 403호"), Location.of(1.0, 2.0))));
//        assertTrue(result.containsKey(Academy.of(AcademyInfo.of("뮤즈아카데미", "02-987-6543", ""), Address.of("서울시 강남구 역삼동 123-45 (뮤즈빌딩) 5층"), Location.of(1.0, 2.0))));
//        assertEquals(1, result.get(Academy.of(AcademyInfo.of("원뮤직(ONEMUSIC)스튜디오학원", "031-714-2472", ""), Address.of("경기도 성남시 분당구 느티로 65 (정자동/구미빌딩) 3층 403호"), Location.of(1.0, 2.0))).size());
//        assertEquals(1, result.get(Academy.of(AcademyInfo.of("뮤즈아카데미", "02-987-6543", ""), Address.of("서울시 강남구 역삼동 123-45 (뮤즈빌딩) 5층"), Location.of(1.0, 2.0))).size());
//    }
//
//}
