package com.pcitc.smc.csv.model;

import java.util.Date;

public class ESMEvent {
	private String	eventId;         //	事件ID（事件的唯一标识，由ESM生成）
	private String	baseEventIds;         //	设备日志被ESM标准化后的事件ID（当事件类型为base类型时，其值与eventId相同）
	private String	name;         //	事件名称（对事件的基本特征的描述）
	private String	message;         //	对事件特征的概要性描述（其值为原始日志的某部分内容）
	private String	type;         //	标准化后的ESM事件的类型，包括base,correlation,aggretation
	//private Date	endTime;         //	事件的结束时间
	private String endTime;
	private String	applicationProtocol;         //	事件的应用协议，如http,smtp,fpt等
	private String	transportProtocol;         //	事件的传输协议，如tcp，udp等
	private String	bytesIn;         //	事件的传入字节数（inbound）
	private String	bytesOut;         //	事件的传出字节数（outbound）
	private String	customer;         //	客户信息
	private String	customerName;         //	客户名称
	private Date	startTime;         //	事件的开始时间
	//private String startTime;
	private String	managerReceiptTime;         //	ESM管理器接收到事件的时间
	private String	categorySignificance;         //	类别意义：从网络入侵检测角度对事件的描述
	private String	categoryTechnique;         //	类别技术：描述对客体执行某项动作所采用的方法
	private String	categoryBehavior;         //	类别行为：描述针对客体或由客体所执行的动作
	private String	categoryDeviceGroup;         //	类别设备组：事件所涉的设备类型描述
	private String	categoryObject;         //	类别对象：描述事件焦点所涉的物理的或虚拟的客体
	private String	categoryOutcome;         //	类别结果：标识对客体所执行动作的结果
	private String	fileName;         //	文件名称（file即为：事件中所涉及到的具体文件）
	private String	filePath;         //	文件路径
	private String	requestCookies;         //	请求的Cookie值
	private String	requestMethod;         //	请求的方法，如get，post等
	private String	requestProtocol;         //	请求的协议，如http,https等
	private String	requestUrl;         //	请求的路径（通常为Web的请求路径）
	private String	requestUrlFileName;         //	请求路径文件名（Web请求中所涉及到的具体文件名称，如某个js文件等）
	private String	requestUrlPort;         //	请求路径端口，如80,8080，8443等
	private String	requestUrlHost;         //	请求路径主机（Web请求中所涉及到的主机名称）
	private String	requestUrlQuery;         //	请求路径查询（请求的URL中用来请求资源所使用的查询）
	private String	requestContext;         //	请求的上下文
	private String	sourceAddress;         //	事件的源地址
	private String	sourceAssetName;         //	事件的源资产名称（源地址对应的资产设备名称）
	private String	sourceHostName;         //	事件的源主机名
	private String	sourceMacAddress;         //	事件的源MAC地址
	private String	sourcePort;         //	事件的源端口
	private String	sourceProcessName;         //	事件的源进程名
	private String	sourceServiceName;         //	事件的源服务名称
	private String	sourceTranslatedAddress;         //	事件的源NAT转换地址
	private String	sourceTranslatedPort;         //	事件的源NAT转换端口
	private String	sourceUserId;         //	事件的源用户ID
	private String	sourceUserName;         //	事件的源用户名称
	private String	sourceZoneName;         //	事件的源网络区域名称
	private String	sourceGeoCountryName;         //	事件的目标地理位置信息-定位到所在国家
	private String	sourceCeoLocationInfo;         //	事件的目标地理位置信息-定位到所在城市
	private String	targetAddress;         //	事件的目标地址
	private String	targetAssetName;         //	事件的目标资产名称（目标地址对应的资产设备名称）
	private String	targetHostName;         //	事件的目标主机名
	private String	targetMacAddress;         //	事件的目标MAC地址
	private String	targetPort;         //	事件的目标端口
	private String	targetProcessName;         //	事件的目标进程名
	private String	targetServiceName;         //	事件的目标服务名称
	private String	targetTranslatedAddress;         //	事件的目标NAT转换地址
	private String	targetTranslatedPort;         //	事件的目标NAT转换端口
	private String	targetUserId;         //	事件的目标用户ID
	private String	targetUserName;         //	事件的目标用户名称
	private String	targetZoneName;         //	事件的目标网络区域名称
	private String	targetGeoCountryName;         //	事件的目标地理位置信息-定位到所在国家
	private String	targetCeoLocationInfo;         //	事件的目标地理位置信息-定位到所在城市
	private String	assetCriticality;         //	资产的关键度（以资产所承载的业务的重要程度划分）
	private String	modelConfidence;         //	模型可信度（目标资产是否在ESM资产库中建模，若为是，则模型可信度为高；否则为低，其值为0-10）
	private String	priority;         //	优先级（响应事件的优先级别，其值为0-10）
	private String	relevance;         //	事件所涉行为其结果成功的可能性（其值为0-10）
	private String	severity;         //	事件所涉行为对目标造成后果的严重程度（其值为0-10）
	private String	deviceCustomString1;         //	设备自定义字符串（不在ESM标准字段内的值）
	private String	deviceCustomString2;         //	设备自定义字符串（不在ESM标准字段内的值）
	private String	deviceCustomString3;         //	设备自定义字符串（不在ESM标准字段内的值）
	private String	deviceCustomString4;         //	设备自定义字符串（不在ESM标准字段内的值）
	private String	deviceCustomString5;         //	设备自定义字符串（不在ESM标准字段内的值）
	private String	deviceCustomString6;         //	设备自定义字符串（不在ESM标准字段内的值）
	private String	deviceCustomNumber1;         //	设备自定义数字（不在ESM标准字段内的值）
	private String	deviceCustomNumber2;         //	设备自定义数字（不在ESM标准字段内的值）
	private String	deviceCustomNumber3;         //	设备自定义数字（不在ESM标准字段内的值）
	private String	deviceCustomDate1;         //	设备自定义时间（不在ESM标准字段内的值）
	private String	deviceCustomDate2;         //	设备自定义时间（不在ESM标准字段内的值）
	private String	deviceAction;         //	设备动作（设备指：产生日志的设备，其动作如防火墙设备的accept,reject,drop等）
	private String	deviceAddress;         //	设备地址（产生日志设备的IP地址）
	private String	deviceAssetId;         //	设备资产Id（设备可作为ESM资产库中的一个资产，资产ID为其在资产库中的唯一标识）
	private String	deviceAssetName;         //	设备的资产名称
	private String	deviceDirection;         //	设备方向（设备产生日志的矢量，如inbound 或 outbound）
	private String	deviceDomain;         //	设备所在的域
	private String	deviceEventCategory;         //	设备事件类别（设备在产生日志时对日志的类型描述，如Windows日志的security，system或application）
	private String	deviceEventClassId;         //	设备日志类别ID（设备产生的日志其类型的标识符，如IDS设备的规则ID等）
	private String	deviceFacility;         //	产生日志的设备子模块（如IDS设备的sensor等）
	private String	deviceHostName;         //	设备的主机名称
	private String	deviceInboundInterface;         //	设备的带内inbound接口名称
	private String	deviceOutboundInterface;         //	设备的带外inbound接口名称
	private String	deviceProcessId;         //	产生日志设备的对应的进程ID
	private String	deviceProcessName;         //	产生日志设备的对应的进程名称
	private String	deviceProduct;         //	设备产品（预定义的值，其值如Firewall，Anti-Virus，IPS，IDS等）
	private String	deviceReceiptTime;         //	设备接收到日志的时间戳（日志在传输到ESM前可能会在其设备组间进行多次传递）
	private String	deviceSeverity;         //	设备严重度（设备在产生日志时定义的日志级别，如防火墙日志的info,warning,alert,critical等）
	private String	deviceTranslatedAddress;         //	设备的转换地址（NAT转换地址）
	private String	deviceVendor;         //	设备厂商名称（如Cisco，Venustec，Microsoft，Oracle，IBM等）
	private String	deviceZone;         //	设备网络区域（设备所在的网络区域）
	private String	deviceZoneName;         //	设备网络区域名称（设备所在的网络区域名称）
	private String	agentAddress;         //	ESM采集器的IP地址
	private String	agentHostName;         //	ESM采集器的主机名称
	private String	agentName;         //	ESM采集器的名称
	private String	agentReceiptTime;         //	ESM采集器接收到日志的时间戳
	private String	agentSeverity;         //	ESM采集器标准化日志时对日志所涉行为造成危害的的影响程度（其值为veryLow,low,medium,high,veryHigh）
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getBaseEventIds() {
		return baseEventIds;
	}
	public void setBaseEventIds(String baseEventIds) {
		this.baseEventIds = baseEventIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/*public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}*/
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getApplicationProtocol() {
		return applicationProtocol;
	}
	public void setApplicationProtocol(String applicationProtocol) {
		this.applicationProtocol = applicationProtocol;
	}
	public String getTransportProtocol() {
		return transportProtocol;
	}
	public void setTransportProtocol(String transportProtocol) {
		this.transportProtocol = transportProtocol;
	}
	public String getBytesIn() {
		return bytesIn;
	}
	public void setBytesIn(String bytesIn) {
		this.bytesIn = bytesIn;
	}
	public String getBytesOut() {
		return bytesOut;
	}
	public void setBytesOut(String bytesOut) {
		this.bytesOut = bytesOut;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/*public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}*/
	public String getManagerReceiptTime() {
		return managerReceiptTime;
	}
	public void setManagerReceiptTime(String managerReceiptTime) {
		this.managerReceiptTime = managerReceiptTime;
	}
	public String getCategorySignificance() {
		return categorySignificance;
	}
	public void setCategorySignificance(String categorySignificance) {
		this.categorySignificance = categorySignificance;
	}
	public String getCategoryTechnique() {
		return categoryTechnique;
	}
	public void setCategoryTechnique(String categoryTechnique) {
		this.categoryTechnique = categoryTechnique;
	}
	public String getCategoryBehavior() {
		return categoryBehavior;
	}
	public void setCategoryBehavior(String categoryBehavior) {
		this.categoryBehavior = categoryBehavior;
	}
	public String getCategoryDeviceGroup() {
		return categoryDeviceGroup;
	}
	public void setCategoryDeviceGroup(String categoryDeviceGroup) {
		this.categoryDeviceGroup = categoryDeviceGroup;
	}
	public String getCategoryObject() {
		return categoryObject;
	}
	public void setCategoryObject(String categoryObject) {
		this.categoryObject = categoryObject;
	}
	public String getCategoryOutcome() {
		return categoryOutcome;
	}
	public void setCategoryOutcome(String categoryOutcome) {
		this.categoryOutcome = categoryOutcome;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRequestCookies() {
		return requestCookies;
	}
	public void setRequestCookies(String requestCookies) {
		this.requestCookies = requestCookies;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getRequestProtocol() {
		return requestProtocol;
	}
	public void setRequestProtocol(String requestProtocol) {
		this.requestProtocol = requestProtocol;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getRequestUrlFileName() {
		return requestUrlFileName;
	}
	public void setRequestUrlFileName(String requestUrlFileName) {
		this.requestUrlFileName = requestUrlFileName;
	}
	public String getRequestUrlPort() {
		return requestUrlPort;
	}
	public void setRequestUrlPort(String requestUrlPort) {
		this.requestUrlPort = requestUrlPort;
	}
	public String getRequestUrlHost() {
		return requestUrlHost;
	}
	public void setRequestUrlHost(String requestUrlHost) {
		this.requestUrlHost = requestUrlHost;
	}
	public String getRequestUrlQuery() {
		return requestUrlQuery;
	}
	public void setRequestUrlQuery(String requestUrlQuery) {
		this.requestUrlQuery = requestUrlQuery;
	}
	public String getRequestContext() {
		return requestContext;
	}
	public void setRequestContext(String requestContext) {
		this.requestContext = requestContext;
	}
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public String getSourceAssetName() {
		return sourceAssetName;
	}
	public void setSourceAssetName(String sourceAssetName) {
		this.sourceAssetName = sourceAssetName;
	}
	public String getSourceHostName() {
		return sourceHostName;
	}
	public void setSourceHostName(String sourceHostName) {
		this.sourceHostName = sourceHostName;
	}
	public String getSourceMacAddress() {
		return sourceMacAddress;
	}
	public void setSourceMacAddress(String sourceMacAddress) {
		this.sourceMacAddress = sourceMacAddress;
	}
	public String getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}
	public String getSourceProcessName() {
		return sourceProcessName;
	}
	public void setSourceProcessName(String sourceProcessName) {
		this.sourceProcessName = sourceProcessName;
	}
	public String getSourceServiceName() {
		return sourceServiceName;
	}
	public void setSourceServiceName(String sourceServiceName) {
		this.sourceServiceName = sourceServiceName;
	}
	public String getSourceTranslatedAddress() {
		return sourceTranslatedAddress;
	}
	public void setSourceTranslatedAddress(String sourceTranslatedAddress) {
		this.sourceTranslatedAddress = sourceTranslatedAddress;
	}
	public String getSourceTranslatedPort() {
		return sourceTranslatedPort;
	}
	public void setSourceTranslatedPort(String sourceTranslatedPort) {
		this.sourceTranslatedPort = sourceTranslatedPort;
	}
	public String getSourceUserId() {
		return sourceUserId;
	}
	public void setSourceUserId(String sourceUserId) {
		this.sourceUserId = sourceUserId;
	}
	public String getSourceUserName() {
		return sourceUserName;
	}
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}
	public String getSourceZoneName() {
		return sourceZoneName;
	}
	public void setSourceZoneName(String sourceZoneName) {
		this.sourceZoneName = sourceZoneName;
	}
	public String getSourceGeoCountryName() {
		return sourceGeoCountryName;
	}
	public void setSourceGeoCountryName(String sourceGeoCountryName) {
		this.sourceGeoCountryName = sourceGeoCountryName;
	}
	public String getSourceCeoLocationInfo() {
		return sourceCeoLocationInfo;
	}
	public void setSourceCeoLocationInfo(String sourceCeoLocationInfo) {
		this.sourceCeoLocationInfo = sourceCeoLocationInfo;
	}
	public String getTargetAddress() {
		return targetAddress;
	}
	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}
	public String getTargetAssetName() {
		return targetAssetName;
	}
	public void setTargetAssetName(String targetAssetName) {
		this.targetAssetName = targetAssetName;
	}
	public String getTargetHostName() {
		return targetHostName;
	}
	public void setTargetHostName(String targetHostName) {
		this.targetHostName = targetHostName;
	}
	public String getTargetMacAddress() {
		return targetMacAddress;
	}
	public void setTargetMacAddress(String targetMacAddress) {
		this.targetMacAddress = targetMacAddress;
	}
	public String getTargetPort() {
		return targetPort;
	}
	public void setTargetPort(String targetPort) {
		this.targetPort = targetPort;
	}
	public String getTargetProcessName() {
		return targetProcessName;
	}
	public void setTargetProcessName(String targetProcessName) {
		this.targetProcessName = targetProcessName;
	}
	public String getTargetServiceName() {
		return targetServiceName;
	}
	public void setTargetServiceName(String targetServiceName) {
		this.targetServiceName = targetServiceName;
	}
	public String getTargetTranslatedAddress() {
		return targetTranslatedAddress;
	}
	public void setTargetTranslatedAddress(String targetTranslatedAddress) {
		this.targetTranslatedAddress = targetTranslatedAddress;
	}
	public String getTargetTranslatedPort() {
		return targetTranslatedPort;
	}
	public void setTargetTranslatedPort(String targetTranslatedPort) {
		this.targetTranslatedPort = targetTranslatedPort;
	}
	public String getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getTargetUserName() {
		return targetUserName;
	}
	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}
	public String getTargetZoneName() {
		return targetZoneName;
	}
	public void setTargetZoneName(String targetZoneName) {
		this.targetZoneName = targetZoneName;
	}
	public String getTargetGeoCountryName() {
		return targetGeoCountryName;
	}
	public void setTargetGeoCountryName(String targetGeoCountryName) {
		this.targetGeoCountryName = targetGeoCountryName;
	}
	public String getTargetCeoLocationInfo() {
		return targetCeoLocationInfo;
	}
	public void setTargetCeoLocationInfo(String targetCeoLocationInfo) {
		this.targetCeoLocationInfo = targetCeoLocationInfo;
	}
	public String getAssetCriticality() {
		return assetCriticality;
	}
	public void setAssetCriticality(String assetCriticality) {
		this.assetCriticality = assetCriticality;
	}
	public String getModelConfidence() {
		return modelConfidence;
	}
	public void setModelConfidence(String modelConfidence) {
		this.modelConfidence = modelConfidence;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getRelevance() {
		return relevance;
	}
	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getDeviceCustomString1() {
		return deviceCustomString1;
	}
	public void setDeviceCustomString1(String deviceCustomString1) {
		this.deviceCustomString1 = deviceCustomString1;
	}
	public String getDeviceCustomString2() {
		return deviceCustomString2;
	}
	public void setDeviceCustomString2(String deviceCustomString2) {
		this.deviceCustomString2 = deviceCustomString2;
	}
	public String getDeviceCustomString3() {
		return deviceCustomString3;
	}
	public void setDeviceCustomString3(String deviceCustomString3) {
		this.deviceCustomString3 = deviceCustomString3;
	}
	public String getDeviceCustomString4() {
		return deviceCustomString4;
	}
	public void setDeviceCustomString4(String deviceCustomString4) {
		this.deviceCustomString4 = deviceCustomString4;
	}
	public String getDeviceCustomString5() {
		return deviceCustomString5;
	}
	public void setDeviceCustomString5(String deviceCustomString5) {
		this.deviceCustomString5 = deviceCustomString5;
	}
	public String getDeviceCustomString6() {
		return deviceCustomString6;
	}
	public void setDeviceCustomString6(String deviceCustomString6) {
		this.deviceCustomString6 = deviceCustomString6;
	}
	public String getDeviceCustomNumber1() {
		return deviceCustomNumber1;
	}
	public void setDeviceCustomNumber1(String deviceCustomNumber1) {
		this.deviceCustomNumber1 = deviceCustomNumber1;
	}
	public String getDeviceCustomNumber2() {
		return deviceCustomNumber2;
	}
	public void setDeviceCustomNumber2(String deviceCustomNumber2) {
		this.deviceCustomNumber2 = deviceCustomNumber2;
	}
	public String getDeviceCustomNumber3() {
		return deviceCustomNumber3;
	}
	public void setDeviceCustomNumber3(String deviceCustomNumber3) {
		this.deviceCustomNumber3 = deviceCustomNumber3;
	}
	public String getDeviceCustomDate1() {
		return deviceCustomDate1;
	}
	public void setDeviceCustomDate1(String deviceCustomDate1) {
		this.deviceCustomDate1 = deviceCustomDate1;
	}
	public String getDeviceCustomDate2() {
		return deviceCustomDate2;
	}
	public void setDeviceCustomDate2(String deviceCustomDate2) {
		this.deviceCustomDate2 = deviceCustomDate2;
	}
	public String getDeviceAction() {
		return deviceAction;
	}
	public void setDeviceAction(String deviceAction) {
		this.deviceAction = deviceAction;
	}
	public String getDeviceAddress() {
		return deviceAddress;
	}
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	public String getDeviceAssetId() {
		return deviceAssetId;
	}
	public void setDeviceAssetId(String deviceAssetId) {
		this.deviceAssetId = deviceAssetId;
	}
	public String getDeviceAssetName() {
		return deviceAssetName;
	}
	public void setDeviceAssetName(String deviceAssetName) {
		this.deviceAssetName = deviceAssetName;
	}
	public String getDeviceDirection() {
		return deviceDirection;
	}
	public void setDeviceDirection(String deviceDirection) {
		this.deviceDirection = deviceDirection;
	}
	public String getDeviceDomain() {
		return deviceDomain;
	}
	public void setDeviceDomain(String deviceDomain) {
		this.deviceDomain = deviceDomain;
	}
	public String getDeviceEventCategory() {
		return deviceEventCategory;
	}
	public void setDeviceEventCategory(String deviceEventCategory) {
		this.deviceEventCategory = deviceEventCategory;
	}
	public String getDeviceEventClassId() {
		return deviceEventClassId;
	}
	public void setDeviceEventClassId(String deviceEventClassId) {
		this.deviceEventClassId = deviceEventClassId;
	}
	public String getDeviceFacility() {
		return deviceFacility;
	}
	public void setDeviceFacility(String deviceFacility) {
		this.deviceFacility = deviceFacility;
	}
	public String getDeviceHostName() {
		return deviceHostName;
	}
	public void setDeviceHostName(String deviceHostName) {
		this.deviceHostName = deviceHostName;
	}
	public String getDeviceInboundInterface() {
		return deviceInboundInterface;
	}
	public void setDeviceInboundInterface(String deviceInboundInterface) {
		this.deviceInboundInterface = deviceInboundInterface;
	}
	public String getDeviceOutboundInterface() {
		return deviceOutboundInterface;
	}
	public void setDeviceOutboundInterface(String deviceOutboundInterface) {
		this.deviceOutboundInterface = deviceOutboundInterface;
	}
	public String getDeviceProcessId() {
		return deviceProcessId;
	}
	public void setDeviceProcessId(String deviceProcessId) {
		this.deviceProcessId = deviceProcessId;
	}
	public String getDeviceProcessName() {
		return deviceProcessName;
	}
	public void setDeviceProcessName(String deviceProcessName) {
		this.deviceProcessName = deviceProcessName;
	}
	public String getDeviceProduct() {
		return deviceProduct;
	}
	public void setDeviceProduct(String deviceProduct) {
		this.deviceProduct = deviceProduct;
	}
	public String getDeviceReceiptTime() {
		return deviceReceiptTime;
	}
	public void setDeviceReceiptTime(String deviceReceiptTime) {
		this.deviceReceiptTime = deviceReceiptTime;
	}
	public String getDeviceSeverity() {
		return deviceSeverity;
	}
	public void setDeviceSeverity(String deviceSeverity) {
		this.deviceSeverity = deviceSeverity;
	}
	public String getDeviceTranslatedAddress() {
		return deviceTranslatedAddress;
	}
	public void setDeviceTranslatedAddress(String deviceTranslatedAddress) {
		this.deviceTranslatedAddress = deviceTranslatedAddress;
	}
	public String getDeviceVendor() {
		return deviceVendor;
	}
	public void setDeviceVendor(String deviceVendor) {
		this.deviceVendor = deviceVendor;
	}
	public String getDeviceZone() {
		return deviceZone;
	}
	public void setDeviceZone(String deviceZone) {
		this.deviceZone = deviceZone;
	}
	public String getDeviceZoneName() {
		return deviceZoneName;
	}
	public void setDeviceZoneName(String deviceZoneName) {
		this.deviceZoneName = deviceZoneName;
	}
	public String getAgentAddress() {
		return agentAddress;
	}
	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}
	public String getAgentHostName() {
		return agentHostName;
	}
	public void setAgentHostName(String agentHostName) {
		this.agentHostName = agentHostName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentReceiptTime() {
		return agentReceiptTime;
	}
	public void setAgentReceiptTime(String agentReceiptTime) {
		this.agentReceiptTime = agentReceiptTime;
	}
	public String getAgentSeverity() {
		return agentSeverity;
	}
	public void setAgentSeverity(String agentSeverity) {
		this.agentSeverity = agentSeverity;
	}

}
